package com.example.fundraisinghome.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.fundraisinghome.model.CartItem
import com.example.fundraisinghome.model.OrderItem
import com.example.fundraisinghome.model.Order_
import com.example.fundraisinghome.model.PaymentDetails
import com.example.fundraisinghome.model.PaymentDetails_
import com.example.fundraisinghome.model.Product
import com.example.fundraisinghome.model.ShippingDetails_
import com.example.fundraisinghome.model.User

@Database(entities = [User::class, Product::class, CartItem::class, Order_::class, OrderItem::class, ShippingDetails_::class, PaymentDetails_::class], version = 5, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun cartItemDao(): CartItemDao
    abstract fun productDao(): ProductDao

    abstract fun orderDao(): OrderDao
    abstract fun orderItemDao(): OrderItemDao
    abstract fun shippingDetailsDao() : ShippingDetailsDao
    abstract fun paymentDetailsDao() : PaymentDetailsDao

    companion object{
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context) : AppDatabase{
            val tempInstance = INSTANCE

            if(tempInstance != null) {
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "user_database1"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }

        }
    }
}