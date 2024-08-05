import React, { useState } from 'react'
import Login from './Login'
import Register from './Register'

const LandingPage = ({serverurl}) => {

    const [isLogin,setIsLogin] = useState(true)
    const [isRegister,setIsRegister] = useState(false)

    const openregister = () =>{
        setIsRegister(true)
        setIsLogin(false)
    }

    const openlogin = () =>{
        setIsLogin(true)
        setIsRegister(false)
    }

  return (
    <div className='LandingPage'>
        <div>
        <div>
            <div className='btncls'>
                <button onClick={openregister}>Register</button>
                <button onClick={openlogin}>Login</button>
            </div>
        </div>
        <div className='pgs'>
            {(isLogin)?<Login serverurl={serverurl}/>:""}
            {(isRegister)?<Register serverurl={serverurl}/>:""}
        </div>
        </div>
    </div>
  )
}

export default LandingPage