package core;

import core.engine.DecisionEngine;
import core.model.Product;
import core.model.UserPreference;
import core.repository.CSVProductReader;

import java.util.*;

public class Main {


    public static void main(String[] args) {


        Scanner scanner = new Scanner(System.in);


        System.out.println("==============================");
        System.out.println("       Welcome to DecisionIQ");
        System.out.println("==============================");


        System.out.print("Enter your budget: ");
        double budget = scanner.nextDouble();

        scanner.nextLine();


        System.out.print("Enter Category: ");
        String category = scanner.nextLine();



        CSVProductReader reader =
                new CSVProductReader();


        List<Product> products =
                reader.readProducts(
                        "Datasets/products.csv"
                );



        Set<String> availableFeatures =
                new HashSet<>();


        for(Product product : products){


            if(product.getCategory()
                    .equalsIgnoreCase(category)){


                product.getFeatures()
                        .forEach(feature ->
                                availableFeatures.add(
                                        feature.getName()
                                )
                        );
            }
        }



        UserPreference preference =
                new UserPreference(
                        budget,
                        category
                );



        System.out.println("\nSet feature importance (0-100)");



        for(String feature :
                availableFeatures){


            System.out.print(
                    feature + " importance: "
            );


            int importance =
                    scanner.nextInt();


            preference.addPreference(
                    feature,
                    importance
            );
        }



        DecisionEngine engine =
                new DecisionEngine();


        List<Product> recommendations =
                engine.recommend(
                        products,
                        preference
                );



        System.out.println("\n==============================");
        System.out.println(" Top Recommendations");
        System.out.println("==============================");


        int rank = 1;


        for(Product product :
                recommendations){


            System.out.println(
                    "Rank #" + rank++
            );


            System.out.println(product);


            System.out.println();
        }


        scanner.close();
    }
}