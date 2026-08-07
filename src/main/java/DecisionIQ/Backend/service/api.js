const API_URL="http://localhost:8080/api/recommend";


export async function getRecommendations(data){

    const response = await fetch(API_URL,{
        method:"POST",
        headers:{
            "Content-Type":"application/json"
        },
        body:JSON.stringify(data)
    });


    return response.json();
}