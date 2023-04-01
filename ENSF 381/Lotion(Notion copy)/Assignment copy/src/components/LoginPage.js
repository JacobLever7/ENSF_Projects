import React from "react";

function LoginPage({ login }) 
{
    return (
        <div className = "no-note-content-container">
            <div className = "login-button">
                <button onClick={() => login()}>Sign in to Lotion with Google 🚀 </button>
            </div>
        </div>
    );
}

export default LoginPage;