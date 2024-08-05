import React,{ useState } from 'react'

const Register = ({serverurl}) => {

    const [user_name,setUser_Name] = useState("")
    const [user_email,setUser_Email] = useState("")
    const [user_password,setUser_Password] = useState("")
    const [confirmPasssword,setConfirmPasssword] = useState("")
    const [errorMsg,setErrorMsg] = useState("")

    const register = async () => {
        const response = await fetch(
            `${serverurl}/register`,{
              method:'POST',
              header:{'Context-Type':'application/json'},
              body:JSON.stringify({user_name,user_email,user_password,confirmPasssword})
            })  
        const result = await response.text()
        setErrorMsg(result)
    }

 
  return (
    <div className='Register'>
        <div><h2>Registration Form</h2></div>
        <h4 className='msg'>{errorMsg}</h4>
        <div className='registration'>
            <div>
                <div>
                    <span>
                        Enter User Name:
                        <input type="text" placeholder='User Name' value={user_name} onChange={(e)=>(setUser_Name(e.target.value))} required/>
                    </span>
                </div>
                <div>
                    <span>
                        Enter User Email:
                        <input type="mail" placeholder='User Email' value={user_email} onChange={(e)=>(setUser_Email(e.target.value))} required/>
                    </span>
                </div>
                <div>
                    <span>
                        Enter NPassword:
                        <input type="password" placeholder='Password' value={user_password} onChange={(e)=>(setUser_Password(e.target.value))} required/>
                    </span>
                </div>
                <div>
                    <span>
                        Retype Password:
                        <input type="text" placeholder='Retype Password' value={confirmPasssword} onChange={(e)=>(setConfirmPasssword(e.target.value))} required/>
                    </span>
                </div>
            </div>
        </div>
        <div>
            <button onClick={register}>Register</button>
        </div>
    </div>
  )
}

export default Register