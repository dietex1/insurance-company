import { API } from "../config"

const testClient = {
    client_id: '0',
    first_name: "Name",
    last_name: "lastName",
    age: 111,
    income: 111,
    work_place: "work"
}



class ClientDataSource {
    async getAll() {
        const resp = await fetch(`${API}/clients`)
        const clients = await resp.json()

        return clients
    }

    async removeById(id) {
        await fetch(`${API}/clients/${id}`, {method: "DELETE"})
    }

    async create(client) {
        const clientReq = JSON.stringify(client)
        await fetch(`${API}/clients`, {
            method: "POST",
            body: clientReq,
            headers: {
                'Content-Type': 'application/json'
            }
        })

    }

    async update(updatedClient) {
        const clientReq = JSON.stringify(updatedClient)
        await fetch(`${API}/clients/${updatedClient.client_id}`, {
            method: "PUT",
            body: clientReq,
            headers: {
                'Content-Type': 'application/json'
            }
        })
    }
}

export const clientDataSource = new ClientDataSource()