import React from 'react'
import { Link } from 'react-router-dom'
import { HiLogout } from "react-icons/hi";


const Homepagenavbar = () => {

  const usrname = sessionStorage.getItem("usrname")

  const logout = () =>{
    sessionStorage.setItem("token","")
    sessionStorage.setItem("usrname","")
  }

  return (
    <nav className='Homepagenavbar'>
      <div>
        <div><Link to="/Home/">Home</Link></div>
        <div><Link to="/Home/Addtask">Add Task</Link></div>
        <div><Link to="/Home/Tasklist">Task List</Link></div>
      </div>
      <div>
        <span>
          <span>Welcome, </span>
          <span>{usrname}</span>
          <span> <Link to="/"><HiLogout onClick={logout}/></Link></span>
        </span>
      </div>
    </nav>
  )
}

export default Homepagenavbar