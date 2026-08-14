import { useState } from "react";
import "./App.css";
import { getRecommendations } from "./api/decisionApi";

function App() {
  const [budget, setBudget] = useState(50000);
  const [performance, setPerformance] = useState(80);
  const [battery, setBattery] = useState(80);
  const [display, setDisplay] = useState(80);
  const [value, setValue] = useState(80);

  const [recommendations, setRecommendations] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  const handleRecommendations = async () => {
    setLoading(true);
    setError("");
    setRecommendations([]);

    try {
      const data = await getRecommendations(budget, {
        performance,
        battery,
        display,
        value,
      });

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
          Budget: ₹{Number(budget).toLocaleString("en-IN")}

          <input
            type="number"
            min="0"
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
          Display Priority: {display}

          <input
            type="range"
            min="0"
            max="100"
            value={display}
            onChange={(e) => setDisplay(e.target.value)}
          />
        </label>

        <label>
          Value Priority: {value}

          <input
            type="range"
            min="0"
            max="100"
            value={value}
            onChange={(e) => setValue(e.target.value)}
          />
        </label>

        <button onClick={handleRecommendations} disabled={loading}>
          {loading ? "Finding..." : "Get Recommendations"}
        </button>
      </div>

      {error && <p className="error">{error}</p>}

      {recommendations.length > 0 && (
        <div className="recommendations">
          {recommendations.map((result, index) => (
            <div
              className={`product-card ${
                index === 0 ? "best-match" : ""
              }`}
              key={result.product.id || index}
            >
              {index === 0 && (
                <div className="best-match-badge">
                  🏆 BEST MATCH
                </div>
              )}

              <h2>{result.product.name}</h2>

              <p>
                <strong>Brand:</strong> {result.product.brand}
              </p>

              <p>
                <strong>Category:</strong> {result.product.category}
              </p>

              <p>
                <strong>Price:</strong>{" "}
                ₹{result.product.price.toLocaleString("en-IN")}
              </p>

              <div className="score">
                <span>Recommendation Score</span>
                <strong>{result.score.toFixed(2)}</strong>
              </div>

              <h3>Why this product?</h3>

              <ul>
                {result.reasons.map((reason, reasonIndex) => (
                  <li key={reasonIndex}>{reason}</li>
                ))}
              </ul>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}

export default App;