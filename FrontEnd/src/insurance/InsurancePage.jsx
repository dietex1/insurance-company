import { useEffect, useState } from 'react'
import { insuranceDataSource } from './InsuranceDataSource'
import './table2.css'


export const InsurancePage = (props) => {
    const [insurance, setInsurance] = useState([])
    const [updateForm, setUpdateForm] = useState({
        open: false,
        insurance: {
            insurance_id: 0,
            start_date: "2023-01-04",
            end_date: "2023-10-10",
            price: 111,
            type_of_insurance: "Jyype",
            client: 1,
            agency_id: []
        }
    })

    const refreshInsurance = () => {
        insuranceDataSource.getAll()
        .then(cl => setInsurance(cl))
    }

    useEffect(() => {
        refreshInsurance()
    }, [])

    const handleDelete = async (id) => {
        await insuranceDataSource.removeById(id)
        refreshInsurance()
    }

    const handleSave = async (e) => {
        e.preventDefault()
        const data = e.target
        const insurance = {
            insurance_id: 1,
            start_date: data.start_date.value,
            end_date: data.end_date.value,
            price: data.price.value,
            type_of_insurance: data.type_of_insurance.value,
            client: data.client.value,
            agency_id: []
        }
        
        await insuranceDataSource.create(insurance)
        refreshInsurance()
    }

    const handleUpdate = async (e) => {
        e.preventDefault()
        const data = e.target
        const insurance = {
            insurance_id: data.id.value,
            start_date: data.start_date.value,
            end_date: data.end_date.value,
            price: data.price.value,
            type_of_insurance: data.type_of_insurance.value,
            client: data.client.value,
            agency_id: []
        }
        
        await insuranceDataSource.update(insurance)
        refreshInsurance()
    }

    return (
        <div>
            { updateForm.open &&
            <form onSubmit={handleUpdate}>
                <ul>
                <li>
                    <label>
                        Id:
                        <input disabled  type="number" name="id" value={updateForm.insurance.insurance_id}/>
                    </label>
                    </li>
                    <li>
                    <label>
                        Start Date:
                        <input type="text" name="start_date" value={updateForm.insurance.start_date} onChange={(e) => {
                            setUpdateForm(f => ({...f, insurance: {
                                ...f.insurance,
                                start_date: e.target.value
                            }}))
                        }}/>
                    </label>
                    </li>
                  
                    <li>
                    <label>
                        End Date:
                        <input type="text" name="end_date" value={updateForm.insurance.end_date} onChange={(e) => {
                            setUpdateForm(f => ({...f, insurance: {
                                ...f.insurance,
                                end_date: e.target.value
                            }}))
                        }}/>
                    </label>
                    </li>
                    <li>
                    <label>
                        Price:
                        <input type="number" name="price" value={updateForm.insurance.price} onChange={(e) => {
                            setUpdateForm(f => ({...f, insurance: {
                                ...f.insurance,
                                price: e.target.value
                            }}))
                        }}/>
                    </label>
                    </li>
                    <li>
                    <label>
                        Type of Insurance:
                        <input type="text" name="type_of_insurance" value={updateForm.insurance.type_of_insurance} onChange={(e) => {
                            setUpdateForm(f => ({...f, insurance: {
                                ...f.insurance,
                                type_of_insurance: e.target.value
                            }}))
                        }}/>
                    </label>
                    </li>
                    <li>
                        <label>
                            Client:
                            <input type="number" name="client" value={updateForm.insurance.client} onChange={(e) => {
                                setUpdateForm(f => ({...f, insurance: {
                                        ...f.insurance,
                                        client: e.target.value
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
                        Start Date:
                        <input type="text" name="start_date" />
                    </label>
                    </li>
                  
                    <li>
                    <label>
                        End Date:
                        <input type="text" name="end_date" />
                    </label>
                    </li>
                    <li>
                    <label>
                        price:
                        <input type="number" name="price" />
                    </label>
                    </li>
                    <li>
                    <label>
                        Type of Insurance:
                        <input type="text" name="type_of_insurance" />
                    </label>
                    </li>
                    <li>
                        <label>
                            Client:
                            <input type="number" name="client" />
                        </label>
                    </li>
                </ul>
                
            <input type="submit" value="SAVE" />
            </form>

            <table className="my-table" border={0}>
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Start date</th>
                    <th>End date</th>
                    <th>price</th>
                    <th>Type of Insurance</th>
                    <th>Client</th>
                    <th>actions</th>
                </tr>   
                </thead>
<tbody>
{
                    insurance.map(i => 
                        <tr key={i.insurance_id}>
                            <td>{i.insurance_id}</td>
                            <td>{i.start_date}</td>
                            <td>{i.end_date}</td>
                            <td>{i.price}</td>
                            <td>{i.type_of_insurance}</td>
                            <td>{i.client}</td>

                            <td>
                                <button onClick={() => {
handleDelete(i.insurance_id)
                            }}> DELETE </button>
                             <button onClick={() => {
setUpdateForm({open: true, insurance: {
    ...i
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