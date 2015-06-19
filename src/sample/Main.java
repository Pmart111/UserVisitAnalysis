package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.VisitInformation.*;
import sample.VisitInformation.SiteActions.SiteActionFactory;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

import java.io.*;
import java.util.Random;

public class Main extends Application {
    public static Stage mainStage;
    @Override
    public void start(Stage primaryStage) throws Exception{
        mainStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
        primaryStage.setTitle("OOP Lab");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }


    public static void main(String[] args) {
    //    generateData("data.vds");
        launch(args);
    }

    public static void generateData(String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        HashMap<String, Double> prices = new HashMap<String, Double>();
        prices.put("http://sc2tv.ru", 5.0);
        prices.put("http://joyreactor.cc", 6.0);
        prices.put("http://pikabu.ru", 7.0);
        prices.put("http://ukr.net", 5.0);
        prices.put("http://lenta.ru", 6.0);
        prices.put("http://24smi.org", 4.0);
        Iterator iterator = prices.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.write((((String) entry.getKey() + " " + entry.getValue().toString() + "\r\n")).getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        try {
            fileOutputStream.write("\t".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 1000; i++) {
            Visit visit = new Visit();
            FromInformation fromInformation = new FromInformation();
            switch (random.nextInt(4)) {
                case 0: {
                    fromInformation.setFromType(FromType.SITE);
                    int n = random.nextInt(100);
                    if (n < 10) {
                        fromInformation.setSiteName("http://sc2tv.ru");
                    } else if (n < 50) {
                        fromInformation.setSiteName("http://joyreactor.cc");
                    } else {
                        fromInformation.setSiteName("http://pikabu.ru");
                    }
                }
                break;
                case 1: {
                    fromInformation.setFromType(FromType.NEWS);
                    int n = random.nextInt(100);
                    if (n < 25) {
                        fromInformation.setSiteName("http://ukr.net");
                    } else if (n < 66) {
                        fromInformation.setSiteName("http://lenta.ru");
                    } else {
                        fromInformation.setSiteName("http://24smi.org");
                    }
                }
                break;
                case 2: {
                    fromInformation.setFromType(FromType.SENGINE);
                    int n = random.nextInt(100);
                    if (n < 35) {
                        fromInformation.setSiteName("http://yandex.ru");
                    } else {
                        fromInformation.setSiteName("http://google.com");
                    }
                }
                break;
                case 3: {
                    fromInformation.setFromType(FromType.OTHER);
                    fromInformation.setSiteName("-");
                }
                break;
            }
            WhenInformation whenInformation = new WhenInformation();
            Calendar calendar = Calendar.getInstance();
            calendar.set(2015, 6, random.nextInt(5) + 5, (int) Math.abs(-Math.abs((random.nextGaussian() + 5)) + 24), random.nextInt(60), random.nextInt(60));
            whenInformation.setDate(calendar.getTime());
            whenInformation.setTimeAfterBannerDisplay(new Date((long)((10000 * Math.abs(random.nextGaussian() + 1)))));

            visit.setFromInformation(fromInformation);
            visit.setWhenInformation(whenInformation);
            int actCount = random.nextInt(5) + 1;
            for (int j = 0; j < actCount; j++) {
                try {
                    switch (random.nextInt(3)) {
                        case 0: {
                            visit.getSiteActions().add(SiteActionFactory.get(SiteActionType.CALL, new Date((0)), null));
                        }
                        break;
                        case 1: {
                            visit.getSiteActions().add(SiteActionFactory.get(SiteActionType.PRODUCTPAGEENTER, new Date((0)), "Product#" + random.nextInt(100) + 1));
                        }
                        break;
                        case 2: {
                            String product = "Product#" + random.nextInt(100) + 1;
                            visit.getSiteActions().add(SiteActionFactory.get(SiteActionType.PRODUCTPAGEENTER, new Date((0)), product));
                            visit.getSiteActions().add(SiteActionFactory.get(SiteActionType.PURCHASE, new Date((0)), product));
                        }
                        break;
                    }
                }
                catch (WrongSiteActionTypeException e) {
                    e.printStackTrace();
                }
            }
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.write((visit.toString() + "\r\n").getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
