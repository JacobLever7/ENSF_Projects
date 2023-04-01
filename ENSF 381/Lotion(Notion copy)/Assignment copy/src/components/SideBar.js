import React from "react";
import { NavLink } from "react-router-dom";

function SideBar({ sideBarCollapsed, notes, addNotes, formatDate }){

    const parse = require("html-react-parser");
    return (
        <div className= {`side-bar-container ${sideBarCollapsed ? "hidden" : ""}`}>
            <div className="side-bar-top-container">
                <div className="side-bar-title">
                    <h3>Notes</h3>
                </div>
                <div className="button-container">
                    <button onClick={addNotes}>&#10133;</button>
                </div>
            </div>
            {notes.length === 0 ? 
            (
            <div className="side-bar-body-container">
                <p>No Note Yet</p>
            </div>) : 
            (
            <div className="side-bar-note-container">
                {notes.map((note) => (
                <NavLink to={`/notes/${note.id}`} key={note.id}>
                    <div className="side-bar-note-item">
                        <h3>{note.title.length > 25 ? note.title.substr(0, 25) + "..." : note.title}</h3>
                        <small>{formatDate(note.date)}</small>
                        <div className="note-item-content">
                            {note.content ? parse(note.content.substr(0, 30)): "..." } 
                        </div>
                    </div>
                </NavLink>
                
            ))}
            </div>
        )}
        </div>
    );
}

export default SideBar;