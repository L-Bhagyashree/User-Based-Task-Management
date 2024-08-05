import React, { useEffect , useState } from 'react'
import { FiEdit } from "react-icons/fi";
import { MdOutlineDeleteOutline } from "react-icons/md";
import Edittasks from './Edittasks';
import { IoMdCloseCircleOutline } from "react-icons/io";


const Tasklist = ({serverurl}) => {

    const [tskarry,setTskarry] = useState([])
    const [msg,setMsg] = useState('')
    const [refresh,setRefresh] = useState(0)
    const [title,setTitle] = useState("")
    const [desc,setDesc] = useState("")
    const [id,setId] = useState("")
    const [isOpen, setIsOpen] = useState(false);

    const edttask = (task_id,task_header,task_desc) =>{
        setTitle(task_header)
        setDesc(task_desc)
        setId(task_id)
        setIsOpen(true)
    }


    const delete_task = async (task_id) => {
        const response =  await fetch(
            `${serverurl}/deletetask`,{
              method:'POST',
              header:{'Context-Type':'application/json'},
              body:JSON.stringify({task_id})
            })
        if(response.ok){
            const result = await response.json()
            setMsg(result.message)
            setRefresh(refresh+1)
        }
        
    }

    useEffect(()=>{
        const user_id = sessionStorage.getItem("token")

        const get_task_list = async () =>{
            const response = await fetch(
                `${serverurl}/showtask`,{
                  method:'POST',
                  header:{'Context-Type':'application/json'},
                  body:JSON.stringify({user_id})
                })
            if(response.ok){
                const result = await response.json()
                setTskarry(result)
            }
        }
        
        get_task_list()
    },[serverurl,refresh])

    useEffect(() => {
        const dialog = document.getElementById('myDialog');
        if (isOpen) {
          dialog.showModal();
        } else {
          dialog.close();
        }
      }, [isOpen]);

  return (
    <div className='Tasklist'>
        <div className='msg'>{msg}</div>
        <table>
            <thead>
                <tr>
                    <th>
                        Title
                    </th>
                    <th>
                        Description
                    </th>
                    <th>
                        Action
                    </th>
                </tr>
            </thead>
            <tbody>
                {(()=>(Array.isArray(tskarry)))?tskarry.map((item,index)=>(<tr key={index}><td>{item.task_header}</td><td>{item.task_desc}</td><td><FiEdit onClick={()=>{edttask(item.task_id,item.task_header,item.task_desc)}}/><MdOutlineDeleteOutline onClick={()=>{delete_task(item.task_id)}}/></td></tr>)):"No Task"}
            </tbody>
        </table>
        <div className='myDialog'>
            <dialog id="myDialog">
                <IoMdCloseCircleOutline onClick={() => setIsOpen(false)}/>
                <Edittasks serverurl={serverurl} title={title} desc={desc} id={id} setRefresh={setRefresh} refresh={refresh}/> 
            </dialog>
        </div>
    </div>
  )
}

export default Tasklist