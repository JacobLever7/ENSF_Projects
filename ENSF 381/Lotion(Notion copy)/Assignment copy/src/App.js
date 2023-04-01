//import dependancies here
import React, { useState, useEffect } from "react";
import { BrowserRouter, Routes, Route} from "react-router-dom";
import Layout from "./components/Layout";
import NoNoteContent from "./components/NoNoteContent";
import Content from "./components/Content";
import EditContent from "./components/EditContent";
import LoginPage from "./components/LoginPage";
import PageNotFound from "./components/PageNotFound";
import uuid from "react-uuid";
import { googleLogout, useGoogleLogin } from '@react-oauth/google';
import axios from 'axios';



function App() {
  const [ user, setUser ] = useState();
  const [ profile, setProfile ] = useState(localStorage.profile? JSON.parse(localStorage.profile) : null);
  const [ notes, setNotes ] = useState(localStorage.notes? JSON.parse(localStorage.notes) : []);


  //google functions
  const login = useGoogleLogin({
    onSuccess: (codeResponse) => setUser(codeResponse),
    onError: (error) => console.log('Login Failed:', error)
  });

  useEffect(
    () => {
      setProfile(localStorage.profile? JSON.parse(localStorage.profile) : null);
        if (user) {
          axios
            .get(`https://www.googleapis.com/oauth2/v1/userinfo?access_token=${user.access_token}`, {
                headers: {
                    Authorization: `Bearer ${user.access_token}`,
                    Accept: 'application/json'
                }
            })
            .then((res) => {
                setProfile(res.data);
            })
            .catch((err) => console.log(err));
        }
    },
    [ user ]
  );

  useEffect(()=> {
    if ( profile )
    {
      const loadNotes = async () => {
        const res = await fetch(
          `https://wlaqzwydk2ykbif3c4xcy3xek40tttjg.lambda-url.ca-central-1.on.aws?email=${profile.email}`,
          {
            method: "GET",
            headers: {
              "Content-Type": "application/json"
            },
          }
        )
        const jsonRes = await res.json();
        if (res.status === 200)
        {
            setNotes([...jsonRes["notes"]["Items"]]);
        }
        else
        {
            window.alert(`Error! status ${res.status}\n${jsonRes["message"]}`);
        }
      }
      loadNotes();
    }
    else{
      setNotes([]);
    }

  },
  [ profile ]
  );



  // log out function to log the user out of google and set the profile array to null
  const logOut = () => {
    setNotes([]);
    googleLogout();
    setProfile(null);
  };

  const options = {
    year: "numeric",
    month: "long",
    day: "numeric",
    hour: "numeric",
    minute: "numeric",
  };

  const formatDate = (when) => {
      const formatted = new Date(when).toLocaleString("en-US", options);
      if (formatted === "Invalid Date") {
          return "";
      }
      return formatted;
  };


  const addNotes = async () => {
    const newDate = new Date()
    const newNote = {
      id: uuid(),
      title: "Untitled",
      content: "",
      date: newDate,
    };
    const res = await fetch(
      "https://cyfljsdgjflnhghhxtmyhz45tu0zuese.lambda-url.ca-central-1.on.aws/",
      {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify({...newNote, email: profile.email}),
      }
    );
    const jsonRes = await res.json();
    if (res.status === 200)
    {
      setNotes([newNote, ...notes]);
    }
    else
    {
        window.alert(`Error! status ${res.status}\n${jsonRes["message"]}`);
    }

  };
  
  useEffect(() => {
    localStorage.setItem("profile", JSON.stringify(profile));
  }, [profile]);

  useEffect(() => {
    localStorage.setItem("notes", JSON.stringify(notes));
  }, [notes]);

  return ( 
        <BrowserRouter>
          <Routes>
            <Route path="/" element={<Layout notes = { notes } addNotes = { addNotes } profile = { profile } logOut = {logOut} formatDate = { formatDate }/>}>
              {!profile ? (
                <>
                <Route index element={<LoginPage login = { login }/>}/>
                <Route path="*" element={<PageNotFound profile = { profile }/>}/>
                </>
              ) : (
                <>
                  <Route index element={<NoNoteContent/>}/>
                  <Route path="/notes" element={<NoNoteContent/>}/>
                  <Route path="/notes/:noteId" element={<Content notes = { notes } setNotes = { setNotes } formatDate = { formatDate } profile = { profile }/>}/>
                  <Route path="/notes/:noteId/edit" element={<EditContent notes = { notes } setNotes ={ setNotes } profile = { profile }/>} />
                  <Route path="*" element={<PageNotFound profile = { profile }/>}/>
                </>
              )}
            </Route>
          </Routes>
        </BrowserRouter>
  );
}

export default App;