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
      <header className="hero">
        <div className="hero-badge">AI PRODUCT DECISION ENGINE</div>

        <h1>DecisionIQ</h1>

        <p>
          Smart product recommendations based on your budget and priorities.
        </p>
      </header>

      <section className="form-section">
        <div className="section-heading">
          <span>01</span>
          <div>
            <h2>Your Requirements</h2>
            <p>Tell DecisionIQ what matters most to you.</p>
          </div>
        </div>

        <div className="form">
          <label>
            <div className="label-row">
              <span>Budget</span>
              <strong>
                ₹{Number(budget).toLocaleString("en-IN")}
              </strong>
            </div>

            <input
              type="number"
              min="0"
              value={budget}
              onChange={(e) => setBudget(e.target.value)}
            />
          </label>

          <label>
            <div className="label-row">
              <span>Performance Priority</span>
              <strong>{performance}</strong>
            </div>

            <input
              type="range"
              min="0"
              max="100"
              value={performance}
              onChange={(e) => setPerformance(e.target.value)}
            />
          </label>

          <label>
            <div className="label-row">
              <span>Battery Priority</span>
              <strong>{battery}</strong>
            </div>

            <input
              type="range"
              min="0"
              max="100"
              value={battery}
              onChange={(e) => setBattery(e.target.value)}
            />
          </label>

          <label>
            <div className="label-row">
              <span>Display Priority</span>
              <strong>{display}</strong>
            </div>

            <input
              type="range"
              min="0"
              max="100"
              value={display}
              onChange={(e) => setDisplay(e.target.value)}
            />
          </label>

          <label>
            <div className="label-row">
              <span>Value Priority</span>
              <strong>{value}</strong>
            </div>

            <input
              type="range"
              min="0"
              max="100"
              value={value}
              onChange={(e) => setValue(e.target.value)}
            />
          </label>

          <button
            className="recommend-button"
            onClick={handleRecommendations}
            disabled={loading}
          >
            {loading ? "ANALYZING..." : "GET RECOMMENDATIONS"}
            {!loading && <span>→</span>}
          </button>
        </div>
      </section>

      {error && <div className="error">{error}</div>}

      {recommendations.length > 0 && (
        <section className="results-section">
          <div className="section-heading">
            <span>02</span>
            <div>
              <h2>Recommended For You</h2>
              <p>
                Ranked according to your budget and selected priorities.
              </p>
            </div>
          </div>

          <div className="recommendations">
            {recommendations.map((result, index) => (
              <article
                className={`product-card ${
                  index === 0 ? "best-match" : ""
                }`}
                key={result.product.id || index}
              >
                {index === 0 && (
                  <div className="best-match-badge">
                    <span>★</span>
                    BEST MATCH
                  </div>
                )}

                <div className="product-top">
                  <div>
                    <span className="product-category">
                      {result.product.category}
                    </span>

                    <h3>{result.product.name}</h3>

                    <p className="brand">
                      {result.product.brand}
                    </p>
                  </div>

                  <div className="score">
                    <span>IQ SCORE</span>
                    <strong>{result.score.toFixed(2)}</strong>
                  </div>
                </div>

                <div className="price">
                  ₹{result.product.price.toLocaleString("en-IN")}
                </div>

                <div className="features">
                  {result.product.features.map((feature, featureIndex) => (
                    <div className="feature" key={featureIndex}>
                      <div className="feature-heading">
                        <span>{feature.name}</span>
                        <strong>{feature.score}</strong>
                      </div>

                      <div className="feature-bar">
                        <div
                          className="feature-progress"
                          style={{
                            width: `${feature.score}%`,
                          }}
                        />
                      </div>
                    </div>
                  ))}
                </div>

                <div className="why">
                  <h4>Why this product?</h4>

                  <ul>
                    {result.reasons.map((reason, reasonIndex) => (
                      <li key={reasonIndex}>{reason}</li>
                    ))}
                  </ul>
                </div>
              </article>
            ))}
          </div>
        </section>
      )}
    </div>
  );
}

export default App;