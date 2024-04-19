package com.example.fundraisinghome.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.res.stringResource
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.fundraisinghome.R

@Entity(tableName = "product_table")
data class Product(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val productId: String,
    val name: String,
    val description: String,
    val price: Double
)

val productList = listOf(
    Product(
        productId = "product1",
        name = "Dog Collar",
        description = "Elevate your pet\\'s style while supporting animal welfare with our charitable dog collar. Every purchase of this collar contributes directly to our fundraising efforts for animal shelters and rescue organizations. Crafted with premium materials for durability and comfort, this collar is adjustable to fit dogs of all sizes, ensuring a perfect and secure fit. This not only enhance your pet\\'s appearance but also symbolize your commitment to responsible pet care and compassion for animals in need. Make a difference in the lives of pets while keeping your furry friend safe and stylish with our fundraising dog collar.",
        price = 1.0
    ),
    Product(
        productId = "product2",
        name = "Meal Bowl",
        description = "Nourish your pet with love and support a great cause with our fundraising feeding bowl. Every purchase of this bowl contributes directly to our efforts in supporting animal shelters and rescue organizations. Crafted with high-quality, pet-safe materials, this feeding bowl is designed to provide a comfortable and enjoyable dining experience for your furry friend. Its sturdy construction ensures durability and stability, while the non-slip base prevents spills and messes during mealtime. Make mealtime meaningful by giving back to animals in need while providing your pet with a reliable and stylish feeding solution.",
        price = 2.0
    ),
    Product(
        productId = "product3",
        name = "Shaving Kit",
        description = "Elevate your pet grooming routine and support animal welfare with our comprehensive grooming supply set. This set includes everything you need to keep your furry friend looking and feeling their best, while also contributing to our fundraising efforts for animal shelters and rescue organizations. Set includes Scented Shampoo, Blow Dryer, Tweezers, Comb, Gloves and other essential grooming tools such as nail clippers, ear wipes, and a grooming apron for convenience and efficiency.",
        price = 3.0
    ),
    Product(
        productId = "product4",
        name = "Water Bottle",
        description = "Stay hydrated and inspired with our Fundraising Water Bottle featuring time markings and motivational messages! This durable and eco-friendly bottle helps you track your daily water intake with convenient time markers, encouraging you to stay hydrated throughout the day. On the side, you\\'ll find uplifting messages promoting health and self-care, providing a daily dose of motivation and positivity. Every purchase of this water bottle supports our fundraising efforts for health and wellness initiatives, making a meaningful impact on your well-being and the community. Stay refreshed, motivated, and make hydration a joyful habit with our Fundraising Water Bottle.",
        price = 4.0
    ),
    Product(
        productId = "product5",
        name = "Flex Band",
        description = "Transform your fitness routine and support a healthier you with our Fundraising Resistance Bands. These versatile and durable bands offer a full-body workout experience, perfect for strength training, flexibility exercises, and rehabilitation workouts. Made from high-quality latex material, our bands provide varying levels of resistance to accommodate different fitness levels and goals. Portable and easy to use, you can take your workout anywhere, whether at home, in the gym, or while traveling. With every purchase of our Resistance Bands, you not only invest in your fitness journey but also contribute to our fundraising efforts for health and wellness initiatives. Take the next step towards a stronger, fitter you while making a positive impact on your health and the community. Embrace the power of resistance training with our Fundraising Resistance Bands today!",
        price = 5.0
    ),
    Product(
        productId = "product6",
        name = "Home Scents",
        description = "This uplifting reed diffuser is scented with exotic fruits, including mango, tangerine, passion fruit, and pomegranate. Its aroma reaches every corner of a room, and we think that its minimal bottle lends itself to the fragrance. You can buy refills and its vessel is recyclable.",
        price = 6.0
    ),
    Product(
        productId = "product7",
        name = "Zen Seat",
        description = "Experience tranquility and comfort like never before with our Zen Seat. Crafted with the finest materials and designed for ultimate relaxation, our seat encourages proper posture while providing unparalleled support. Whether you\\'re meditating, reading, or simply unwinding after a long day, our Zen Seat effortlessly adapts to your body, offering a serene oasis in any space. Embrace mindfulness and elevate your relaxation with the perfect companion for your moments of peace.",
        price = 7.0
    ),

    Product(
        productId = "product8",
        name = "STEM Kit",
        description = "STEM Projects for Kids Age 8-12]STEM Toys 6-in-1 Solar Robot Kit, including a Space Station, Space Explorer, Space Rover, Space Shuttle, Astronaut, and Space Dog. Start with your favorite model and build your own space fleet. Perfect for 8-12 year olds.",
        price = 8.0
    ),

    Product(
        productId = "product9",
        name = "Art Work",
        description = "Canvas Wall Art Abstract Art Paintings Blue Fantasy Colorful Graffiti on White Background Modern Artwork wall Decor for Living Room Bedroom Kitchen\n" +
                "     Size: 20 in x40 in\n" +
                "     Pattern: abstract painting peinture art\n" +
                "     Waterproof: Yes\n" +
                "     ECO-ink print: Yes\n" +
                "     UV resistant: Yes\n" +
                "     Material: Wood and Canvas\n" +
                "     Type: Giclee Printed\n" +
                "     Ready hanging, no handy work to screw the accessory kits.\n" +
                "\n" +
                "     PACKAGE:\n" +
                "     All canvas wall art are wrapped on frames, bracket mounted, protected by plastic bags and put in carton box finally.",
        price = 9.0
    ),
    Product(
        productId = "product10",
        name = "Mug",
        description = "Add a fashion-forward touch to your kitchenware with this retro-chic Rustic style mug. Crafted out of stoneware, this Green and white Mug Features a wide base for spacious 14oz capacity complemented by its simple yet striking design and vintage charm. Other colours available.",
        price = 10.0
    ),
    Product(
        productId = "product11",
        name = "Tote Bag",
        description = "Our stylish handbag is not only beautiful, but also very durable with a weight of 1 kg. We use high-quality materials and sewing technology. Very suitable for school bags, shopping bags, sports bags, laundry bags, beach bags, travel bags, laptop bags, food bags, etc. This wonderful handbag will give you more. Perfect for grocery shopping, gym, school, work, travel, beach, hiking, camping, weekends, sleepovers or daily use. You can go anywhere.",
        price = 11.0
    ),
    Product(
        productId = "product12",
        name = "School Kit",
        description = "Our 20 Piece School Supply Kit now has even more supplies at a great value! Pack all essential supplies needed for a productive school year. You’ll be at ease knowing school supply shopping just got a little bit easier. ",
        price = 12.0
    ),

)