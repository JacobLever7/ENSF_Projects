import React from "react";
import { useNavigate } from "react-router";

function Header ({ setSideBarCollapsed, profile, logOut }) {

    const navigate = useNavigate();

    const handleLogOut = () => {
        navigate("/");
        logOut();
    }

    return (
        <nav className="header-container">
            <div className="button-container toggle-button">
                <button onClick={ setSideBarCollapsed }>&#9776;</button>
            </div>
            <div className="title">
                <h1>Lotion</h1>
                <p>Like Notion, but worse</p>
            </div>

            {!profile ? (
                <></>
            ):(
            <>
                <div className="user-info-container">
                    <div className="user-email">
                    <p>{profile.email}</p>
                    </div>
                    <div className="logout-button">
                        <button onClick={handleLogOut}>Log out</button> 
                    </div>
                </div>
            </>
            )}
        </nav>
    );
};

export default Header;
