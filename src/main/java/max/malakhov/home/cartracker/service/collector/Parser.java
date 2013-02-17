package max.malakhov.home.cartracker.service.collector;

import max.malakhov.home.cartracker.domain.Car;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Max Malakhov <malakhovbox@gmail.com>
 * @version 0.1
 * @since 2013-02-17
 */
public class Parser {

    private static final String CLASS_RAW_CARS =  "bodystyle1";

    public Collection<Car> getCars(String html) {

        List<Car> cars = new ArrayList();

        Document doc = Jsoup.parse(html);

        Elements rawCars = doc.getElementsByClass(CLASS_RAW_CARS);
        for(Element rawCar: rawCars ) {
            Elements carDetails = rawCar.getElementsByTag("td");
            String id = carDetails.get(0).attributes().get("id");
            String packag = carDetails.get(1).text();
            String engine = carDetails.get(2).text();
            String number = carDetails.get(3).text();
            String colorCode = carDetails.get(4).child(0).attributes().get("rel");
            String colorName = carDetails.get(4).child(0).attributes().get("title");
            String price = carDetails.get(5).text();
            String title = carDetails.get(6).text();
            cars.add(new Car(id, packag, engine, number, colorCode, colorName, price, title));
        }

        return cars;
    }
}
