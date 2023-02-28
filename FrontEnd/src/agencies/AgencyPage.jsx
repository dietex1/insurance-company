import {useEffect, useState} from 'react'
import {agencyDataSource} from './AgencyDataSource'
import './table3.css'


export const AgencyPage = (props) => {
    const [agencies, setAgency] = useState([])
    const [updateForm, setUpdateForm] = useState({
        open: false,
        agency: {
            agency_id: 0,
            agency_name: "Name",
            city: "city",
            insurance_ids: ""
        }
    })

    const refreshAgency = () => {
        agencyDataSource.getAll()
            .then(cl => setAgency(cl))
    }

    useEffect(() => {
        refreshAgency()
    }, [])

    const handleDelete = async (id) => {
        await agencyDataSource.removeById(id)
        refreshAgency()
    }

    const handleSave = async (e) => {
        e.preventDefault()
        const data = e.target
        const agency = {
            agency_id: 1,
            agency_name: data.name.value,
            city: data.city.value,
            insurance_ids: []
        }

        await agencyDataSource.create(agency)
        refreshAgency()
    }

    const handleUpdate = async (e) => {
        e.preventDefault()
        const data = e.target

        const isValid = (idsStr) => {
            try {
                return idsStr.split(', ').map(strId => parseInt(strId))
            }catch (e) {
                alert('Invalid insurance ids format')
                return false
            }

        }

        const agency = {
            agency_id: data.id.value,
            agency_name: data.name.value,
            city: data.city.value,
            insurance_ids: isValid(data.insurance_ids.value)
        }

        if(agency.insurance_ids === false) {
            return
        }
        await agencyDataSource.update({...agency})
        refreshAgency()
    }

    const showAllExpired = () => {
        agencyDataSource.getAllWithExpiredInsurance()
            .then(cl => setAgency(cl))
    }

    return (
        <div>
            { updateForm.open &&
            <form onSubmit={handleUpdate}>
                <ul>
                    <li>
                        <label>
                            Id:
                            <input disabled  type="number" name="id" value={updateForm.agency.agency_id}/>
                        </label>
                    </li>
                    <li>
                        <label>
                            Agency Name:
                            <input type="text" name="name" value={updateForm.agency.agency_name} onChange={(e) => {
                                setUpdateForm(f => ({...f, agency: {
                                        ...f.agency,
                                        agency_name: e.target.value
                                    }}))
                            }}/>
                        </label>
                    </li>

                    <li>
                        <label>
                            City :
                            <input type="text" name="city" value={updateForm.agency.city} onChange={(e) => {
                                setUpdateForm(f => ({...f, agency: {
                                        ...f.agency,
                                        city: e.target.value
                                    }}))
                            }}/>
                        </label>
                    </li>
                    <li>
                        <label>
                            Insurance ID :
                            <input type="string" name="insurance_ids" value={updateForm.agency.insurance_ids} onChange={(e) => {
                                setUpdateForm(f => ({...f, agency: {
                                        ...f.agency,
                                        insurance_ids: e.target.value
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
                            Agency name:
                            <input type="text" name="name" />
                        </label>
                    </li>

                    <li>
                        <label>
                            City:
                            <input type="text" name="city" />
                        </label>
                    </li>
                    <li>
                        <label>
                            Insurance ID:
                            <input type="number" name="insurance_ids" />
                        </label>
                    </li>
                </ul>

                <input type="submit" value="SAVE" />
            </form>

            <table className="my-table" border={0}>
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Agency name</th>
                    <th>City</th>
                    <th>Insurance ID</th>
                    <th>actions</th>
                </tr>
                </thead>
                <tbody>
                {
                    agencies.map(a =>
                        <tr key={a.agency_id}>
                            <td>{a.agency_id}</td>
                            <td>{a.agency_name}</td>
                            <td>{a.city}</td>
                            <td>{a.insurance_ids.join(', ')}</td>
                            <td>
                                <button onClick={() => {
                                    handleDelete(a.agency_id)
                                }}> DELETE </button>
                                <button onClick={() => {
                                    setUpdateForm({open: true, agency: {
                                            ...a,
                                            insurance_ids: a.insurance_ids.join(', ')
                                        }})
                                }}> UPDATE </button>
                            </td>
                        </tr>
                    )
                }
                </tbody>

            </table>

            <button onClick={showAllExpired}>SHOW EXPIRED</button>
            <button onClick={refreshAgency}>SHOW ALL</button>
        </div>
    )
}