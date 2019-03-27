package net.wepla.campus_planet.base

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import net.wepla.campus_planet.rx.SchedulerProvider
import java.lang.ref.WeakReference

class BaseViewModel<T>(schedulerProvider: SchedulerProvider) : ViewModel() {

    var loading = ObservableBoolean(false)
    var schedulerProvider: SchedulerProvider
        get() = schedulerProvider
        set(value) {
            schedulerProvider = value
        }
    var compositeDisposable: CompositeDisposable
        get() = compositeDisposable;
        set(value) {
            compositeDisposable = value
        }
    var navigator: WeakReference<T>
        get() = navigator;
        set(value) {
            this.navigator = value
        }

    init {
        this.schedulerProvider = schedulerProvider
        this.compositeDisposable = CompositeDisposable();
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }


    fun isLoading(): ObservableBoolean {
        return loading
    }

    fun setLoading(isLoading: Boolean) {
        loading.set(isLoading)
    }


}