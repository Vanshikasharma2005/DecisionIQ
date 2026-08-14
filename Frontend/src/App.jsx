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

  const bestMatch = recommendations[0];

  const priorities = {
    performance: Number(performance),
    battery: Number(battery),
    display: Number(display),
    value: Number(value),
  };

  const formatPrice = (price) =>
    Number(price).toLocaleString("en-IN");

  return (
    <div className="app">
      {/* HEADER */}

      <header className="hero">
        <div className="eyebrow">AI PRODUCT DECISION ENGINE</div>

        <h1>DecisionIQ</h1>

        <p>
          Smart product recommendations based on your budget and priorities.
        </p>
      </header>

      {/* REQUIREMENTS */}

      <section className="requirements-section">
        <div className="section-number">01</div>

        <div className="section-heading">
          <h2>Your Requirements</h2>
          <p>Tell DecisionIQ what matters most to you.</p>
        </div>

        <div className="form">
          <label>
            <span>
              Budget
              <strong>₹{formatPrice(budget)}</strong>
            </span>

            <input
              type="number"
              min="0"
              value={budget}
              onChange={(e) => setBudget(e.target.value)}
            />
          </label>

          <label>
            <span>
              Performance Priority
              <strong>{performance}</strong>
            </span>

            <input
              type="range"
              min="0"
              max="100"
              value={performance}
              onChange={(e) => setPerformance(e.target.value)}
            />
          </label>

          <label>
            <span>
              Battery Priority
              <strong>{battery}</strong>
            </span>

            <input
              type="range"
              min="0"
              max="100"
              value={battery}
              onChange={(e) => setBattery(e.target.value)}
            />
          </label>

          <label>
            <span>
              Display Priority
              <strong>{display}</strong>
            </span>

            <input
              type="range"
              min="0"
              max="100"
              value={display}
              onChange={(e) => setDisplay(e.target.value)}
            />
          </label>

          <label>
            <span>
              Value Priority
              <strong>{value}</strong>
            </span>

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
            {loading ? "ANALYZING..." : "GET RECOMMENDATIONS →"}
          </button>
        </div>
      </section>

      {error && <div className="error">{error}</div>}

      {/* VERDICT */}

      {bestMatch && (
        <section className="verdict-section">
          <div className="verdict-label">DECISIONIQ VERDICT</div>

          <div className="verdict-card">
            <div className="verdict-top">
              <div>
                <span className="best-label">★ BEST MATCH</span>

                <h2>{bestMatch.product.name}</h2>

                <p className="verdict-brand">
                  {bestMatch.product.brand} ·{" "}
                  {bestMatch.product.category}
                </p>
              </div>

              <div className="verdict-score">
                <span>IQ SCORE</span>
                <strong>{bestMatch.score.toFixed(2)}</strong>
              </div>
            </div>

            <div className="verdict-price">
              <div>
                <span>Budget</span>
                <strong>
                  ₹{formatPrice(bestMatch.product.price)}
                </strong>
              </div>

              <div
                className={
                  Number(bestMatch.product.price) <= Number(budget)
                    ? "within-budget"
                    : "over-budget"
                }
              >
                {Number(bestMatch.product.price) <= Number(budget)
                  ? "Within your budget"
                  : "Above your budget"}
              </div>
            </div>

            <div className="priority-grid">
              {bestMatch.product.features.map((feature) => (
                <div className="priority-item" key={feature.name}>
                  <div className="priority-name">
                    <span>{feature.name}</span>
                    <strong>{feature.score}/100</strong>
                  </div>

                  <div className="priority-bar">
                    <div
                      className="priority-fill"
                      style={{
                        width: `${feature.score}%`,
                      }}
                    />
                  </div>

                  <small>
                    Your priority:{" "}
                    {priorities[feature.name] ?? 0}/100
                  </small>
                </div>
              ))}
            </div>
          </div>
        </section>
      )}

      {/* RECOMMENDATIONS */}

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

                <div className="card-top">
                  <span className="category">
                    {result.product.category}
                  </span>

                  <span className="card-score">
                    IQ SCORE {result.score.toFixed(2)}
                  </span>
                </div>

                <h3>{result.product.name}</h3>

                <p className="brand">
                  {result.product.brand}
                </p>

                <div className="card-price">
                  ₹{formatPrice(result.product.price)}
                </div>

                <div className="feature-grid">
                  {result.product.features.map((feature) => (
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
                    {result.reasons.map((reason, reasonIndex) => (
                      <li key={reasonIndex}>
                        <span>✓</span>
                        {reason}
                      </li>
                    ))}
                  </ul>
                </div>
              </article>
            ))}
          </div>

          {recommendations.length > 5 && (
            <button
              className="show-all-button"
              onClick={() => setShowAll((current) => !current)}
            >
              {showAll
                ? "SHOW TOP 5 RECOMMENDATIONS ↑"
                : `SHOW ALL ${recommendations.length} RECOMMENDATIONS ↓`}
            </button>
          )}
        </section>
      )}
    </div>
  );
}

export default App;