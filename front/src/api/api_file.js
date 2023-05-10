import * as API from "./index"

export default {

    getImage: params =>{
        return API.POST("/acr", params)
    },
}