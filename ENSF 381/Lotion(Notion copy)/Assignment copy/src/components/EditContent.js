import React, { useState } from "react";
import { useParams, Link, useNavigate } from "react-router-dom";
import ReactQuill from "react-quill";
import 'react-quill/dist/quill.snow.css';
import moment from 'moment';


function EditContent({ notes, setNotes, profile }) {
    const { noteId } = useParams();
    const currentNote = notes.find((note) => note.id === noteId);

    const navigate = useNavigate();

    const deleteNotes = async () => {
        const answer = window.confirm("Are you sure?");
        if (answer) {
            const res = await fetch(
                `https://hl36v36yrefcdg6ukjhueikuri0olfud.lambda-url.ca-central-1.on.aws?email=${profile.email}&id=${noteId}`,
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
    
    let date = new Date();
   
    const [ editedTitle, setEditedTitle ] = useState(currentNote.title);
    const [ editedContent, setEditedContent] = useState(currentNote.content);
    const [ editedDate, setEditedDate] = useState(currentNote.date ? moment(currentNote.date).format("YYYY-MM-DDTkk:mm") : moment(date).format("YYYY-MM-DDTkk:mm"));

    const handleEditTitle = (event) => {
        setEditedTitle(event.target.value);
    }

    const handleSave = async () => {
        const editedNotes = notes.map((note) => {
            if (note.id === noteId)
            {
                return (
                    {
                        ...note,
                        title: `${editedTitle!== "" ? editedTitle : "Untitled"}`,
                        content: editedContent,
                        date: editedDate,
                    }
                )
            }
            else{return note;}
        });
        const noteToChange = editedNotes.find((note) => note.id === noteId);
        const res = await fetch(
            "https://ulqq3aarmkfh4nohu7b7l5vg6a0ktubi.lambda-url.ca-central-1.on.aws/",
            {
              method: "POST",
              headers: {
                "Content-Type": "application/json"
              },
              body: JSON.stringify({...noteToChange, email: profile.email}),
            }
        );
        const jsonRes = await res.json();
        if (res.status === 200)
        {
            setNotes([...editedNotes]);
        }
        else
        {
            window.alert(`Error! status ${res.status}\n${jsonRes["message"]}`);
        }
    }


    return (
    <div className="content-container">
        <div className="content-top-container">
            <div className="content-top-title-container">
                <input type = "text" className = "content-top-textbox" value = { editedTitle } onChange={ handleEditTitle }
                maxLength= "50" />
                <small className="content-date">
                    <input 
                        type="datetime-local" 
                        className="input my-2"
                        value={ editedDate }
                        onChange={(e) => setEditedDate(e.target.value)}
                    />
                </small>
            </div>
            <div className = "content-top-button-container">
                <Link to={`/notes/${noteId}`} onClick={handleSave}>
                    <div className = "content-button">Save</div>
                </Link>
                <div className = "content-button" onClick={deleteNotes}>Delete</div>
            </div>
        </div>
        <ReactQuill theme="snow" value = { editedContent } onChange = { setEditedContent } className="editor"/>
    </div>
    );
}

export default EditContent;