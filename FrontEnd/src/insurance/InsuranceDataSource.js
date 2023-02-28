import { API } from "../config"

const tInsurance = {
    insurance_id: 0,
    start_date: "2023-01-04",
    end_date: "2023-10-10",
    price: 111,
    type_of_insurance: "work"
}



class InsuranceDataSource {
    async getAll() {
        const resp = await fetch(`${API}/insurance`)
        const insurance = await resp.json()

        return insurance
    }

    async removeById(id) {
        await fetch(`${API}/insurance/${id}`, {method: "DELETE"})
    }

    async create(insurance) {
        const insuranceReq = JSON.stringify(insurance)
        await fetch(`${API}/insurance`, {
            method: "POST",
            body: insuranceReq,
            headers: {
                'Content-Type': 'application/json'
            }
        })

    }

    async update(updateInsurance) {
        const insuranceReq = JSON.stringify(updateInsurance)
        await fetch(`${API}/insurance/${updateInsurance.insurance_id}`, {
            method: "PUT",
            body: insuranceReq,
            headers: {
                'Content-Type': 'application/json'
            }
        })
    }
}

export const insuranceDataSource = new InsuranceDataSource()