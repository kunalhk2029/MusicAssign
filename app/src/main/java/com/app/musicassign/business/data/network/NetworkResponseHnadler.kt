package com.app.musicassign.business.data.network

import com.app.musicassign.business.domain.core.DataState
import com.app.musicassign.business.domain.core.UIComponent
import com.app.musicassign.framework.datasource.network.model.ApiResponse

class NetworkResponseHandler<Data>(
    private val response: ApiResult<Data>
) {

    fun getResult():DataState<Data>{
       return when(response){
            is ApiResult.Success->{
              DataState.Data(response.data)
            }
            is ApiResult.GenericError->{
                DataState.Response(uiComponent = UIComponent.Dialog(title = NetworkConstants.NETWORK_ERROR, description =
                response.message?:NetworkConstants.NETWORK_ERROR_UNKNOWN))
            }
            is ApiResult.NetworkError->{
                DataState.Response(uiComponent = UIComponent.Dialog(title = NetworkConstants.NETWORK_ERROR, description =
                NetworkConstants.NETWORK_ERROR_UNKNOWN))
            }
        }
    }
}