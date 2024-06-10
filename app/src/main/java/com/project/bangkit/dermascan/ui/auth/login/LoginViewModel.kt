
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.bangkit.dermascan.config.ApiConfig
import com.project.bangkit.dermascan.data.UserRepository
import com.project.bangkit.dermascan.data.pref.UserModel
import com.project.bangkit.dermascan.request.RequestLogin
import com.project.bangkit.dermascan.response.LoginResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(private val repository: UserRepository) : ViewModel() {

    private val _loginStatus = MutableLiveData<Boolean>()
    val loginStatus: LiveData<Boolean> = _loginStatus

    val errorMessage = MutableLiveData<String>()

    private val value_= MutableLiveData<LoginResponse>()
    val value: LiveData<LoginResponse> = value_
    var isErrorLogin  :Boolean = false

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean > = _isLoading

    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            repository.saveSession(user)
        }
    }

    fun login(email: String, password: String){
        _isLoading.value = true
        val post = RequestLogin(email, password)
        val client= ApiConfig.getApiService().login(post)

        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>,
            ) {
                _isLoading.value = false
                if (response.isSuccessful && response.body() != null) {
                    value_.value = response.body()
                    _loginStatus.value = true
                } else {
                    errorMessage.value = "Check your email and password or your connection"
                    _loginStatus.value = false
                }
            }
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e("Login User", "onFailure: ${t.message.toString()}")
                _loginStatus.value = false
            }
        })
    }
}