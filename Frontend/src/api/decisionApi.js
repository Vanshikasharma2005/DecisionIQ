const API_URL = "http://localhost:8080/api/recommend";

export async function getRecommendations(budget, preferences) {
  const response = await fetch(API_URL, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      budget: Number(budget),
      preferences: {
        performance: Number(preferences.performance),
        battery: Number(preferences.battery),
        display: Number(preferences.display),
        value: Number(preferences.value),
      },
    }),
  });

  if (!response.ok) {
    throw new Error("Failed to get recommendations");
  }

  return await response.json();
}