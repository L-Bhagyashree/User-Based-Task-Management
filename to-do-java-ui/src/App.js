import './App.css';
import React from 'react'
import LandingPage from './Landing_Pages/LandingPage';
import HomePage from './Home_Pages/HomePage';
import { BrowserRouter, Navigate, Route, Routes } from 'react-router-dom';


const App = () => {

  const serverurl = "http://192.168.1.10:8080/jsontest"



  return (
    <div className='App'>
      <BrowserRouter>
        <Routes>
          <Route path='/' element={<LandingPage serverurl={serverurl}/>}></Route>
          <Route path='/Home/*' Component={()=>(sessionStorage.getItem("token"))?<HomePage serverurl={serverurl}/>:<Navigate to="/"/>}></Route>
        </Routes>
      </BrowserRouter>
    </div>
  )
}

export default App