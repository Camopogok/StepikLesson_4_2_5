import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Main {
    static String firstUrl = "https://dominodomoy.ru";
    static String categoryUrl = "/catalog/";

    public static void main(String[] args) {

        Document categoriesDoc = getDocument(firstUrl + categoryUrl);
        Category [] categories = getCategories(categoriesDoc);
        for (Category category: categories) {
            System.out.println(category.name);
            Document goodsDoc = getDocument(firstUrl + category.categoryUrl);
            Good [] goods = getGoods(goodsDoc);
            for (Good good: goods) {
                System.out.println("     " + good.name + "     " + good.price + "     " + good.photoUrl);
            }
        }
    }

    public static Document getDocument(String url) {
        Document doc = new Document("");

        try {
            Connection connection = Jsoup.connect(url);
            connection.userAgent("Mozilla");
            connection.timeout(5000);
            connection.cookie("cookiename", "val234");
            connection.cookie("cookiename", "val234");
            connection.referrer("http://google.com");
            connection.header("headersecurity", "xyz123");
            doc = connection.get();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return doc;
    }

    public static Category [] getCategories (Document document) {
        Elements categoriesData = document.getElementsByClass("menu-submenu-section");
        int categoriesNumber = categoriesData.size();
        Category [] categories = new Category[categoriesNumber];
        for (int i = 0; i <categoriesNumber; i++) {
            categories[i] = new Category();
            categories[i].name = categoriesData.get(i).getElementsByClass("menu-submenu-section-header").get(0).text();
            categories[i].categoryUrl = categoriesData.get(i).getElementsByClass("menu-submenu-section-header").get(0).select("a").attr("href");
        }
        return categories;
    }

    public static Good [] getGoods (Document document) {
        Elements goodsData = document.getElementsByClass("catalog-section-item");
        int goodsNumber = goodsData.size();
        Good [] goods = new Good[goodsNumber];
        for (int i = 0; i <goodsNumber; i++) {
            goods[i] = new Good();
            goods[i].name = goodsData.get(i).getElementsByClass("catalog-section-item-name").get(0).text();
            goods[i].price = goodsData.get(i).getElementsByClass("catalog-section-item-price-discount").get(0).text();
            goods[i].photoUrl = goodsData.get(i).select("img").attr("abs:data-original");
        }
        return goods;
    }
}

class Category {
    String name;
    String categoryUrl;

    public Category () {
        name = "";
        categoryUrl = "";
    }
}

class Good {
    String name;
    String price;
    String photoUrl;

    public Good() {
        name = "";
        price = "";
        photoUrl = "";
    }
}