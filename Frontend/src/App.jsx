import { useState } from "react";
import "./App.css";

function App() {
  const [budget, setBudget] = useState(50000);
  const [performance, setPerformance] = useState(80);
  const [battery, setBattery] = useState(80);
  const [price, setPrice] = useState(80);

  const [recommendations, setRecommendations] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  const getRecommendations = async () => {
    setLoading(true);
    setError("");

    try {
      const response = await fetch("http://localhost:8080/api/recommend", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          budget: Number(budget),
          preferences: {
            performance: Number(performance),
            battery: Number(battery),
            price: Number(price),
          },
        }),
      });

      if (!response.ok) {
        throw new Error("Failed to get recommendations");
      }

      const data = await response.json();
      setRecommendations(data);
    } catch (err) {
      setError(
        "Could not connect to backend. Make sure Spring Boot is running on port 8080."
      );
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="app">
      <h1>DecisionIQ</h1>
      <p>Smart Product Recommendation System</p>

      <div className="form">
        <label>
          Budget
          <input
            type="number"
            value={budget}
            onChange={(e) => setBudget(e.target.value)}
          />
        </label>

        <label>
          Performance Priority: {performance}
          <input
            type="range"
            min="0"
            max="100"
            value={performance}
            onChange={(e) => setPerformance(e.target.value)}
          />
        </label>

        <label>
          Battery Priority: {battery}
          <input
            type="range"
            min="0"
            max="100"
            value={battery}
            onChange={(e) => setBattery(e.target.value)}
          />
        </label>

        <label>
          Price Priority: {price}
          <input
            type="range"
            min="0"
            max="100"
            value={price}
            onChange={(e) => setPrice(e.target.value)}
          />
        </label>

        <button onClick={getRecommendations} disabled={loading}>
          {loading ? "Finding..." : "Get Recommendations"}
        </button>
      </div>

      {error && <p className="error">{error}</p>}

      <div className="recommendations">
        {recommendations.map((result, index) => (
          <div className="product-card" key={index}>
            <h2>{result.product.name}</h2>

            <p>
              <strong>Brand:</strong> {result.product.brand}
            </p>

            <p>
              <strong>Price:</strong> ₹{result.product.price}
            </p>

            <p>
              <strong>Recommendation Score:</strong>{" "}
              {result.score.toFixed(2)}
            </p>

            <h3>Why this product?</h3>

            <ul>
              {result.reasons.map((reason, reasonIndex) => (
                <li key={reasonIndex}>{reason}</li>
              ))}
            </ul>
          </div>
        ))}
      </div>
    </div>
  );
}

export default App;
