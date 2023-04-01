import React from "react";
import { useParams, Link, useNavigate } from "react-router-dom";

function Content({ notes, setNotes, formatDate, profile }) {
    const { noteId } = useParams();
    const currentNote = notes.find((note) => note.id === noteId);

    const navigate = useNavigate();
    const parse = require("html-react-parser");

    const deleteNotes = async () => {
        const answer = window.confirm("Are you sure?");
        if (answer) {
            const res = await fetch(
                `https://k35teunkrvfreikimtux63gkbi0qndgl.lambda-url.ca-central-1.on.aws?email=${profile.email}&id=${noteId}`,
                {
                  method: "DELETE",
                  headers: {
                    "Content-Type": "application/json"
                  },
                }
              );
              const jsonRes = await res.json();
              if (res.status === 200)
              {
                  setNotes(notes.filter((note) => note.id !== noteId))
                  navigate("/notes");
              }
              else
              {
                  window.alert(`Error! status ${res.status}\n${jsonRes["message"]}`);
              }
        }
    }
    return (
        <div className="content-container">
            <div className="content-top-container">
                <div className="content-top-title-container">
                    <h3 className="content-title">{currentNote.title}</h3>
                    <small className="content-date">{ formatDate(currentNote.date) }</small>
                </div>
                <div className = "content-top-button-container">
                    <Link to={`/notes/${noteId}/edit`}>
                        <div className = "content-button">Edit</div>
                    </Link>
                    <div className = "content-button" onClick={deleteNotes}>Delete</div>
                    
                </div>
            </div>
            <div className="content-body-container">
                {parse(currentNote.content)}
            </div>
        </div>
    );
}

export default Content;