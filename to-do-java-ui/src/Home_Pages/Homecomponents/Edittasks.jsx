import React, { useEffect, useState } from 'react'

const Edittasks = ({serverurl,title,desc,id,setRefresh,refresh}) => {
    const[edittitle,setEdittitle] = useState("")
    const[editDesc,setEditDesc] = useState("")
    const[msg,setMsg] = useState("")
    const task_id = id

    const update = async () =>{
        const task_header = edittitle
        const task_desc = editDesc
        const response = await fetch(`${serverurl}/edittask`,{
            method:'POST',
            header:{'Context-Type':'application/json'},
            body:JSON.stringify({task_id,task_header,task_desc})
        })
        if(response.ok){
            const result = await response.json()
            setRefresh(refresh+1)
            setMsg(result.message)
        }
    }

    useEffect(()=>{
        setEditDesc(desc)
        setEdittitle(title)
    },[title,desc,task_id])


  return (
    <div className='Edittasks'>
        <div className='msg'>{msg}</div>
        <div><span>Edit Title  :</span><span><input type='text' value={edittitle} onChange={(e)=>(setEdittitle(e.target.value))}/></span></div>
        <div><span>Edit Description  :</span><span><input type='text' value={editDesc} onChange={(e)=>(setEditDesc(e.target.value))}/></span></div>
        <div><button onClick={update}>Update</button></div>
    </div>
  )
}

export default Edittasks