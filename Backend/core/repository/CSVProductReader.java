package core.repository;

import core.model.Feature;
import core.model.Product;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class CSVProductReader {


    public List<Product> readProducts(String filePath) {


        List<Product> products = new ArrayList<>();


        try {

            BufferedReader reader =
                    new BufferedReader(
                            new FileReader(filePath)
                    );


            String line;


            // skip header
            reader.readLine();


            while ((line = reader.readLine()) != null) {


                String[] data = line.split(",");


                List<Feature> features =
                        new ArrayList<>();


                String featureData = data[5];


                String[] featurePairs =
                        featureData.split("\\|");


                for(String pair : featurePairs){


                    String[] feature =
                            pair.split(":");


                    String name =
                            feature[0];


                    int score =
                            Integer.parseInt(feature[1]);


                    features.add(
                            new Feature(
                                    name,
                                    score
                            )
                    );
                }


                Product product =
                        new Product(
                                Integer.parseInt(data[0]),
                                data[1],
                                data[2],
                                data[3],
                                Double.parseDouble(data[4]),
                                features
                        );


                products.add(product);
            }


            reader.close();


        } catch(Exception e){

            System.out.println(
                    "Error reading product data"
            );

            e.printStackTrace();
        }


        return products;
    }
}