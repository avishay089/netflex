// HomeScreen.js
import React from "react";
import "./Home.css"; // Create this CSS file

const HomeScreen = () => {
  return (

      <div className="homeScreen__background">
        <div className="homeScreen__content">
          <h1>Unlimited movies, TV shows, and more</h1>
          <h2>Starts at ₪32.90. Cancel anytime.</h2>
          <h3>
            Ready to watch? Enter your email to create or restart your
            membership.
          </h3>
          <form className="homeScreen__form">
            <input type="email" placeholder="Email address" required />
            <button className="homeScreen__button">Get Started</button>
          </form>
        </div>
      </div>

  );
};

export default HomeScreen;
