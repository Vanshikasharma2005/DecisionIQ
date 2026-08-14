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
  const [showAll, setShowAll] = useState(false);

  const handleRecommendations = async () => {
    setLoading(true);
    setError("");
    setRecommendations([]);
    setShowAll(false);

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

  const visibleRecommendations = showAll
    ? recommendations
    : recommendations.slice(0, 5);

  return (
    <main className="app">
      <section className="hero">
        <div className="eyebrow">AI PRODUCT DECISION ENGINE</div>

        <h1>
          Decision<span>IQ</span>
        </h1>

        <p>
          Smart product recommendations based on your budget and priorities.
        </p>
      </section>

      <section className="requirements-section">
        <div className="section-number">01</div>

        <div className="section-heading">
          <h2>Your Requirements</h2>
          <p>Tell DecisionIQ what matters most to you.</p>
        </div>

        <div className="form">
          <div className="budget-control">
            <div className="control-header">
              <label>Budget</label>

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
          </div>

          <div className="priority-grid">
            <div className="priority-control">
              <div className="control-header">
                <label>Performance Priority</label>
                <strong>{performance}</strong>
              </div>

              <input
                type="range"
                min="0"
                max="100"
                value={performance}
                onChange={(e) => setPerformance(e.target.value)}
              />
            </div>

            <div className="priority-control">
              <div className="control-header">
                <label>Battery Priority</label>
                <strong>{battery}</strong>
              </div>

              <input
                type="range"
                min="0"
                max="100"
                value={battery}
                onChange={(e) => setBattery(e.target.value)}
              />
            </div>

            <div className="priority-control">
              <div className="control-header">
                <label>Display Priority</label>
                <strong>{display}</strong>
              </div>

              <input
                type="range"
                min="0"
                max="100"
                value={display}
                onChange={(e) => setDisplay(e.target.value)}
              />
            </div>

            <div className="priority-control">
              <div className="control-header">
                <label>Value Priority</label>
                <strong>{value}</strong>
              </div>

              <input
                type="range"
                min="0"
                max="100"
                value={value}
                onChange={(e) => setValue(e.target.value)}
              />
            </div>
          </div>

          <button
            className="recommend-button"
            onClick={handleRecommendations}
            disabled={loading}
          >
            {loading ? "ANALYZING..." : "GET RECOMMENDATIONS"}

            <span>→</span>
          </button>
        </div>
      </section>

      {error && <div className="error">{error}</div>}

      {recommendations.length > 0 && (
        <section className="recommendations-section">
          <div className="section-number">02</div>

          <div className="section-heading">
            <h2>Recommended For You</h2>

            <p>
              Ranked according to your budget and selected priorities.
            </p>
          </div>

          <div className="recommendations">
            {visibleRecommendations.map((result, index) => (
              <article
                className={`product-card ${
                  index === 0 ? "best-match" : ""
                }`}
                key={result.product.id || index}
              >
                {index === 0 && (
                  <div className="best-match-badge">
                    ★ BEST MATCH
                  </div>
                )}

                <div className="product-top">
                  <span className="category">
                    {result.product.category}
                  </span>

                  <span className="score-label">
                    IQ SCORE

                    <strong>
                      {result.score.toFixed(2)}
                    </strong>
                  </span>
                </div>

                <div className="product-info">
                  <h3>{result.product.name}</h3>

                  <p className="brand">
                    {result.product.brand}
                  </p>

                  <div className="price">
                    ₹
                    {result.product.price.toLocaleString(
                      "en-IN"
                    )}
                  </div>
                </div>

                <div className="features">
                  {result.product.features?.map((feature) => (
                    <div
                      className="feature"
                      key={feature.name}
                    >
                      <span>{feature.name}</span>

                      <strong>{feature.score}</strong>
                    </div>
                  ))}
                </div>

                <div className="reasons">
                  <h4>Why this product?</h4>

                  <ul>
                    {result.reasons.map(
                      (reason, reasonIndex) => (
                        <li key={reasonIndex}>
                          <span>✓</span>
                          {reason}
                        </li>
                      )
                    )}
                  </ul>
                </div>
              </article>
            ))}
          </div>

          {recommendations.length > 5 && (
            <div className="show-all-container">
              <button
                className="show-all-button"
                onClick={() => setShowAll(!showAll)}
              >
                {showAll
                  ? "SHOW TOP 5"
                  : `SHOW ALL ${recommendations.length} RECOMMENDATIONS`}

                <span>{showAll ? "↑" : "↓"}</span>
              </button>
            </div>
          )}
        </section>
      )}
    </main>
  );
}

export default App;