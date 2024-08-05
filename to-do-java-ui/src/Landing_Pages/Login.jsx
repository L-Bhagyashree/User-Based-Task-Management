import React, { useState } from 'react'
import { useNavigate } from 'react-router-dom'

const Login = ({serverurl}) => {

    const [user_name,setUser_name] = useState("")
    const [user_password,setUser_password] = useState("")
    const [errorMsg,setErrorMsg] = useState("")
    const navigate = useNavigate()
    sessionStorage.setItem("token","")
    sessionStorage.setItem("usrname","")

    const loginhandle = async () => {
        const response = await fetch(
            `${serverurl}/login`,{
              method:'POST',
              header:{'Context-Type':'application/json'},
              body:JSON.stringify({user_name,user_password})
            })
        if(response.ok){
            const result = await response.json()
            console.log(result)
            if(result.Userid){
                sessionStorage.setItem("token",result.Userid)
                sessionStorage.setItem("usrname",result.UserName)
                navigate("/Home")
            }
            else{
                setErrorMsg(result.message)
            }
        }
    }
    const cleardata = () =>{
        setUser_name("")
        setUser_password("")
    }
    const vanishmsg = () =>{
        setErrorMsg("")
    }

  return (
    <div className='Login' onClick={vanishmsg}>
        <div><h2>Login Form</h2></div>
        <div className='msg'>{errorMsg}</div>
        <div>
            <div>
                <span>
                    Enter Username:
                    <input type='text' placeholder='User Name' value={user_name} onChange={(e)=>(setUser_name(e.target.value))} required/>
                </span>
            </div>
            <div>
                <span>
                    Enter Password:
                    <input type='password' placeholder='Password' value={user_password} onChange={(e)=>(setUser_password(e.target.value))} required/>
                </span>
            </div>
        </div>
        <div>
            <button onClick={loginhandle}>Login</button>
            <button onClick={cleardata}>Clear</button>
        </div>
    </div>
  )
}

export default Login