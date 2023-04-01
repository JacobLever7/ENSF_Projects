import { Link } from "react-router-dom";

function PageNotFound({ profile }) {

    return (
        <>
            {!profile ? (
            <div className = "no-note-content-container">
                <div className="page-not-found-elements">
                    <h1>404</h1>
                    <p>The page you were looking for doesn't seem to exist anymore</p>
                    <button className= "page-not-found-redirect-div">
                        <Link to="/">
                            <p>Back to Login Page</p>
                        </Link>
                    </button>
                </div>
            </div>
            ):(
            <div className = "no-note-content-container">
                <h4>Select a note, or create a new one.</h4>
            </div>
            )
            }
        </>
    );

}

export default PageNotFound;