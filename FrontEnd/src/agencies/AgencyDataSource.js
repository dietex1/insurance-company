import {API} from "../config"

const testClient = {
    agency_id: '0',
    agency_name: "Name",
    city: "city",
}



class AgencyDataSource {
    async getAll() {
        const resp = await fetch(`${API}/agencies`)
        const agencies = await resp.json()

        return agencies
    }

    async removeById(id) {
        await fetch(`${API}/agencies/${id}`, {method: "DELETE"})
    }

    async create(agency) {
        const agenciesReq = JSON.stringify(agency)
        await fetch(`${API}/agencies`, {
            method: "POST",
            body: agenciesReq,
            headers: {
                'Content-Type': 'application/json'
            }
        })

    }

    async update(updatedAgency) {
        const agencyReq = JSON.stringify(updatedAgency)
        await fetch(`${API}/agencies/${updatedAgency.agency_id}`, {
            method: "PUT",
            body: agencyReq,
            headers: {
                'Content-Type': 'application/json'
            }
        })
    }

    async getAllWithExpiredInsurance() {
        const resp = await fetch(`${API}/agencies/expired`)
        return await resp.json()
    }
}

export const agencyDataSource = new AgencyDataSource()