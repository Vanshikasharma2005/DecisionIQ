package core;


import core.engine.DecisionEngine;
import core.model.Product;
import core.model.RecommendationResult;
import core.model.UserPreference;
import core.repository.CSVProductReader;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;



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




        if(availableFeatures.isEmpty()){


            System.out.println(
                    "No products found for this category."
            );


            scanner.close();
            return;
        }




        UserPreference preference =
                new UserPreference(
                        budget,
                        category
                );




        System.out.println(
                "\nSet feature importance (0-100)"
        );



        for(String feature : availableFeatures){


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




        List<RecommendationResult> recommendations =
                engine.recommend(
                        products,
                        preference
                );




        System.out.println(
                "\n=============================="
        );


        System.out.println(
                " Top Recommendations"
        );


        System.out.println(
                "=============================="
        );



        if(recommendations.isEmpty()){


            System.out.println(
                    "No products available within your budget."
            );


            scanner.close();
            return;
        }




        int rank = 1;



        for(RecommendationResult result :
                recommendations){



            System.out.println(
                    "\nRank #" + rank++
            );



            System.out.println(
                    result.getProduct()
            );



            System.out.println(
                    "Decision Score : "
                    + String.format(
                            "%.2f",
                            result.getScore()
                    )
            );



            System.out.println(
                    "\nWhy selected:"
            );



            if(result.getReasons().isEmpty()){


                System.out.println(
                        "No strong matching features found."
                );

            }
            else{


                for(String reason :
                        result.getReasons()){


                    System.out.println(
                            "✓ " + reason
                    );
                }
            }



            System.out.println(
                    "------------------------------"
            );
        }
        scanner.close();
    }
}