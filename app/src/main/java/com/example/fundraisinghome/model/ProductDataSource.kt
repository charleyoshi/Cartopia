import com.example.fundraisinghome.R
import com.example.fundraisinghome.model.Product

object ProductsRepository {
    val products = listOf(
        Product(
            nameRes = R.string.product1,
            descriptionRes = R.string.product_description1,
            imageRes = R.drawable.dog_collar,
            price = 1.0
        ),
        Product(
            nameRes = R.string.product2,
            descriptionRes = R.string.product_description2,
            imageRes = R.drawable.meal_bowl,
            price = 2.0

        ),
        Product(
            nameRes = R.string.product3,
            descriptionRes = R.string.product_description3,
            imageRes = R.drawable.shaving_kit,
            price = 3.0
        ),
        Product(
            nameRes = R.string.product4,
            descriptionRes = R.string.product_description4,
            imageRes = R.drawable.water_bottle,
            price = 4.0
        ),
        Product(
            nameRes = R.string.product5,
            descriptionRes = R.string.product_description5,
            imageRes = R.drawable.flex_band,
            price = 5.0
        ),
        Product(
            nameRes = R.string.product6,
            descriptionRes = R.string.product_description6,
            imageRes = R.drawable.home_scents,
            price = 6.0
        ),
        Product(
            nameRes = R.string.product7,
            descriptionRes = R.string.product_description7,
            imageRes = R.drawable.zen_seat,
            price = 7.0
        ),
        Product(
            nameRes = R.string.product8,
            descriptionRes = R.string.product_description8,
            imageRes = R.drawable.stem_kit,
            price = 8.0
        ),
        Product(
            nameRes = R.string.product9,
            descriptionRes = R.string.product_description9,
            imageRes = R.drawable.art_work,
            price = 9.0
        ),
        Product(
            nameRes = R.string.product10,
            descriptionRes = R.string.product_description10,
            imageRes = R.drawable.mug,
            price = 10.0
        ),
        Product(
            nameRes = R.string.product11,
            descriptionRes = R.string.product_description11,
            imageRes = R.drawable.tote_bag,
            price = 11.0
        ),
        Product(
            nameRes = R.string.product12,
            descriptionRes = R.string.product_description12,
            imageRes = R.drawable.school_kit,
            price = 12.0
        )

    )
}
