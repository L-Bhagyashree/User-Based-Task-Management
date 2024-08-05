import React, { useState } from 'react'

const Addtask = ({serverurl}) => {

    const [title,setTitle] = useState("")
    const [description,setDescription] = useState("")
    const [msg,setMsg] = useState("")

    const addtask = async () =>{
        const task_header = title
        const task_desc = description
        const user_id = sessionStorage.getItem("token")
        const response = await fetch(
            `${serverurl}/task`,{
              method:'POST',
              header:{'Context-Type':'application/json'},
              body:JSON.stringify({user_id,task_header,task_desc})
            })
        if (response.ok){
            const result = await response.json()
            if(result.message){
                setMsg(result.message)
            }
            setTitle("")
            setDescription("")
        }
    }

  return (
    <div className='Addtask'>
        <div className='msg'>{msg}</div>
        <div>
            <span>Enter Title:</span>
            <span><input type='text' placeholder='Write Title Here ...' value={title} onChange={(e)=>(setTitle(e.target.value))} /></span>
        </div>
        <div>
            <span>Enter Description:</span>
            <span><input type='text' placeholder='Write Description Here ...' value={description} onChange={(e)=>(setDescription(e.target.value))} /></span>
        </div>
        <div>
            <button onClick={addtask}>Submit</button>
        </div>
    </div>
  )
}

export default Addtask