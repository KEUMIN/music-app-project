package com.sist.lastproject.util;

import ch.qos.logback.core.encoder.EchoEncoder;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.snu.ids.kkma.index.Keyword;
import org.snu.ids.kkma.index.KeywordExtractor;
import org.snu.ids.kkma.index.KeywordList;

import java.net.URI;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MelonBot {

    private WebDriver driver;
    private WebElement element;
    private String url;

    private static final String ALBUM_XPATH1 = "/html/body/div/div[3]/div/div[1]/div[4]/form/div/ul/li/div/div/dl/dt/a";
    private static final String ALBUM_XPATH2 = "/html/body/div/div[3]/div/div[1]/div[5]/form/div/ul/li[1]/div/div/dl/dt/a";
    private static final String ALBUM_XPATH3 = "/html/body/div/div[3]/div/div[1]/div[4]/form/div/ul/li[1]/div/div/dl/dt/a";

    private static final String WEB_DRIVER_ID = "webdriver.chrome.driver";
    private static final String WEB_DRIVER_PATH = "C:/Users/mskeu/chromedriver_win32/chromedriver.exe";

    public MelonBot() {
        // WebDriver 경로 설정
        System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);

        // WebDriver 옵션 설정
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-popup-blocking");

        driver = new ChromeDriver(options);

        url = "https://www.melon.com/album/detail.htm?albumId=10908834#cmtpgn=&pageNo=1&sortType=0&srchType=2&srchWord=";
    }

    public String activateBot(String searchWord) throws Exception {
        driver.get("https://www.melon.com");
        driver.findElement(By.xpath("/html/body/div/div[2]/div/div[1]/fieldset/input[1]")).sendKeys(searchWord);
        driver.findElement(By.xpath("/html/body/div/div[2]/div/div[1]/fieldset/button[2]")).click();

        String albumId = null;

        try {
            albumId = getAlbumId(ALBUM_XPATH1);
        } catch (NoSuchElementException ne) {
            try {
                albumId = getAlbumId(ALBUM_XPATH2);
            } catch (Exception e) {
                albumId = getAlbumId(ALBUM_XPATH3);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        StringBuffer totComment = new StringBuffer();
        boolean success1 = true;
        int j = 1;
        while (success1 && j <= 25) {
            try {
                driver.get("https://www.melon.com/album/detail.htm?albumId=" + albumId + "#cmtpgn=&pageNo=" + j + "&sortType=0&srchType=2&srchWord=");

                Thread.sleep(2000);

                boolean success2 = true;
                int i = 1;
                while (success2) {
                    try {
                        element = driver.findElement(By.xpath("/html/body/div[1]/div[3]/div/div/div[6]/div[2]/div[3]/ul/li[" + i + "]/div/div[2]/div[1]/div"));
                        String comment = element.getText();
                        totComment.append(comment);
                        i++;
                    } catch (Exception e) {
                        success2 = false;
                    }
                }
                j++;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return totComment.toString();

    }

    private String getAlbumId(String xpath) throws NoSuchElementException, Exception {
        driver.findElement(By.xpath(xpath)).click();
        String url = driver.getCurrentUrl();
        int idx = url.indexOf("=");
        String albumId = url.substring(idx + 1);
        return albumId;
    }

    public void closeDriver() {
        driver.close();
    }
}