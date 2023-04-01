import React, { useState } from "react";
import { Outlet } from "react-router-dom";
import SideBar from "./SideBar";
import Header from "./Header";

function Layout({ notes, addNotes, profile, logOut, formatDate }) {
  
  const [ sideBarCollapsed, setSideBarCollapsed ] = useState(false);

  return (
    <>
      <Header setSideBarCollapsed = { () => setSideBarCollapsed(!sideBarCollapsed) } profile = { profile } logOut = { logOut }/>
      <div className="body-container">
        {!profile ? (
            <></>
        ):(
            <>
                <SideBar
                sideBarCollapsed={ sideBarCollapsed }
                notes={ notes }
                addNotes={ addNotes }
                formatDate={ formatDate }
                />
            </>
        )}
        <Outlet/>
      </div>
    </>
    );
}

export default Layout;