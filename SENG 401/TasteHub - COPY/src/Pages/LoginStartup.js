import React from "react";
import { useUser } from "../UserContext";
import Login from "./Login";
import SideBar from "../Elements/SideBar";
import { useNavigate } from "react-router-dom";
import tastehubImage from "../images/tastehub.png";
import nameImage from "../images/name.png";

function LoginStartup() {
  const { user } = useUser();
  const navigate = useNavigate();

  React.useEffect(() => {
    if (user) {
      navigate("/global");
    }
  }, [user, navigate]);

  return (
    <div className="startup-screen">
      <div className="big-box">
        <img src={nameImage} alt="Tastehub name logo" />

        <div className="row-container">
          <div className="row">{!user && <Login />}</div>
          <div className="row separator"></div>
        </div>

        {user && <SideBar />}
      </div>
    </div>
  );
}

export default LoginStartup;
