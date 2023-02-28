import { useEffect, useState } from 'react'
import { clientDataSource } from './ClientDataSource'
import './table.css'


export const ClientPage = (props) => {
    const [clients, setClients] = useState([])
    const [updateForm, setUpdateForm] = useState({
        open: false,
        client: {
            client_id: 0,
            first_name: "Name",
            last_name: "lastName",
            age: 111,
            income: 111,
            work_place: "work",
            insurance_ids: []
        }
    })

    const refreshClients = () => {
        clientDataSource.getAll()
        .then(cl => setClients(cl))
    }

    useEffect(() => {
       refreshClients()
    }, [])

    const handleDelete = async (id) => {
        await clientDataSource.removeById(id)
        refreshClients()
    }

    const handleSave = async (e) => {
        e.preventDefault()
        const data = e.target
        const client = {
            client_id: 1,
            first_name: data.name.value,
            last_name: data.lastName.value,
            age: data.age.value,
            income: data.income.value,
            work_place: data.work.value,
            insurance_ids: []
        }
        
        await clientDataSource.create(client)
        refreshClients()
    }

    const handleUpdate = async (e) => {
        e.preventDefault()
        const data = e.target
        const client = {
            client_id: data.id.value,
            first_name: data.name.value,
            last_name: data.lastName.value,
            age: data.age.value,
            income: data.income.value,
            work_place: data.work.value,
            insurance_ids: []
        }
        
        await clientDataSource.update(client)
        refreshClients()
    }

    return (
        <div>
            { updateForm.open &&
            <form onSubmit={handleUpdate}>
                <ul>
                <li>
                    <label>
                        Id:
                        <input disabled  type="number" name="id" value={updateForm.client.client_id}/>
                    </label>
                    </li>
                    <li>
                    <label>
                        Name:
                        <input type="text" name="name" value={updateForm.client.first_name} onChange={(e) => {
                            setUpdateForm(f => ({...f, client: {
                                ...f.client,
                                first_name: e.target.value
                            }}))
                        }}/>
                    </label>
                    </li>
                  
                    <li>
                    <label>
                        Last Name:
                        <input type="text" name="lastName" value={updateForm.client.last_name} onChange={(e) => {
                            setUpdateForm(f => ({...f, client: {
                                ...f.client,
                                last_name: e.target.value
                            }}))
                        }}/>
                    </label>
                    </li>
                    <li>
                    <label>
                        Age:
                        <input type="number" name="age" value={updateForm.client.age} onChange={(e) => {
                            setUpdateForm(f => ({...f, client: {
                                ...f.client,
                                age: e.target.value
                            }}))
                        }}/>
                    </label>
                    </li>
                    <li>
                    <label>
                        Income:
                        <input type="number" name="income" value={updateForm.client.income} onChange={(e) => {
                            setUpdateForm(f => ({...f, client: {
                                ...f.client,
                                income: e.target.value
                            }}))
                        }}/>
                    </label>
                    </li>
                    <li>
                    <label>
                        Work place:
                        <input type="text" name="work" value={updateForm.client.work_place} onChange={(e) => {
                            setUpdateForm(f => ({...f, client: {
                                ...f.client,
                                work_place: e.target.value
                            }}))
                        }}/>
                    </label>
                    </li>
                </ul>
                
            <input type="submit" value="UPDATE" />
            </form>
            }
            <form  onSubmit={handleSave}>
                <ul>
                    <li>
                    <label>
                        Name:
                        <input type="text" name="name" />
                    </label>
                    </li>
                  
                    <li>
                    <label>
                        Last Name:
                        <input type="text" name="lastName" />
                    </label>
                    </li>
                    <li>
                    <label>
                        Age:
                        <input type="number" name="age" />
                    </label>
                    </li>
                    <li>
                    <label>
                        Income:
                        <input type="number" name="income" />
                    </label>
                    </li>
                    <li>
                    <label>
                        Work place:
                        <input type="text" name="work" />
                    </label>
                    </li>
                </ul>
                
            <input type="submit" value="SAVE" />
            </form>

            <table className="my-table" border={0}>
                <thead>
                <tr>
                    <th>ID</th>
                    <th>First name</th>
                    <th>Last name</th>
                    <th>Age</th>
                    <th>Income</th>
                    <th>Work Place</th>
                    <th>actions</th>
                </tr>   
                </thead>
<tbody>
{
                    clients.map(c => 
                        <tr key={c.client_id}>
                            <td>{c.client_id}</td>
                            <td>{c.first_name}</td>
                            <td>{c.last_name}</td>
                            <td>{c.age}</td>
                            <td>{c.income}</td>
                            <td>{c.work_place}</td>
                            <td>
                                <button onClick={() => {
handleDelete(c.client_id)
                            }}> DELETE </button>
                             <button onClick={() => {
setUpdateForm({open: true, client: {
    ...c
}})
                            }}> UPDATE </button>
                            </td>
                        </tr>
                    )
                }
</tbody>
            
            </table>
        </div>
    )
}