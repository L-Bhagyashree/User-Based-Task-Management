import React from 'react'
import Homepagenavbar from './Homepagenavbar'
import { Route ,Routes } from 'react-router-dom'
import Homelandingpage from './Homecomponents/Homelandingpage'
import Addtask from './Homecomponents/Addtask'
import Tasklist from './Homecomponents/Tasklist'

const HomePage = ({serverurl}) => {
  return (
    <div className='HomePage'>
      <div><h1>W</h1><span>elcome </span><h1>T</h1><span>o </span><h1>T</h1><span>ask </span><h1>M</h1><span>anegement</span></div>
      <Homepagenavbar/>
      <div>
        <Routes>
          <Route path='/' element={<Homelandingpage />}></Route>
          <Route path='/addtask' element={<Addtask serverurl = {serverurl}/>}></Route>
          <Route path='/tasklist' element={<Tasklist serverurl = {serverurl}/>}></Route>
        </Routes>
      </div>
    </div>
  )
}

export default HomePage