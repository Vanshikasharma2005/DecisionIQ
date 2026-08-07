package DecisionIQ.Backend.repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import DecisionIQ.Backend.model.Feature;
import DecisionIQ.Backend.model.Product;


public class CSVProductReader {


    public List<Product> readProducts(String filePath) {

        List<Product> products = new ArrayList<>();


        try (
                BufferedReader reader =
                        new BufferedReader(
                                new FileReader(filePath)
                        )
        ) {


            // Skip CSV header
            reader.readLine();


            String line;


            while ((line = reader.readLine()) != null) {


                String[] data = line.split(",");


                List<Feature> features =
                        new ArrayList<>();


                String featureData = data[5];


                String[] featurePairs =
                        featureData.split("\\|");


                for (String pair : featurePairs) {


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


        } catch (Exception e) {


            System.out.println(
                    "Error reading product CSV file"
            );


            e.printStackTrace();

        }


        return products;

    }

}