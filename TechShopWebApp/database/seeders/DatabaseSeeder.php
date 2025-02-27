<?php

namespace Database\Seeders;

// use Illuminate\Database\Console\Seeds\WithoutModelEvents;
use Illuminate\Database\Seeder;


class DatabaseSeeder extends Seeder
{
    /**
     * Seed the application's database.
     */
    public function run(): void
    {
        \App\Models\User::factory()->create([
            'name' => 'client',
            'surname' => 'client',
            'username' => 'cliente',
            'email' => 'cliente@iescarrillo.es',
            'password' => bcrypt('holamundo1234'),
        ]);

        \App\Models\Admin::factory()->create([
            'name' => 'admin',
            'email' => 'admin@iescarrillo.es',
            'password' => bcrypt('holamundo1234'),
            'is_admin' => 1,
        ]);

        \App\Models\Productos::factory()->create([
            'nombre' => 'Portatil Asus TUF',
            'marca' => 'Asus',
            'descripcion' => 'Buen portátil',
            'categoria' => 'Laptops',
            'precio' => '789.23',
            'stock' => '102',
            'img' => 'DNOG0I6xLq0TqeP9Ib8wwfqI7H0jMRGfnJlhm7tl.png',
        ]);

        \App\Models\Productos::factory()->create([
            'nombre' => '1More SonoFlow SE',
            'marca' => '1More',
            'descripcion' => 'Buenos Cascos',
            'categoria' => 'Headphones',
            'precio' => '87.00',
            'stock' => '10',
            'img' => 'NTecs5gLWyqlMXa0G3S3CFgctDXZrKpDg9CGJQ6P.png',
        ]);

        \App\Models\Productos::factory()->create([
            'nombre' => 'Zotac Gaming GeForce RTX 3050 ECO 8GB',
            'marca' => 'Zotac Gaming',
            'descripcion' => 'Buen gráfica',
            'categoria' => 'Graphic Cards',
            'precio' => '229',
            'stock' => '10',
            'img' => 'uRjZ74oyCUcFbQkhDSQayus3sIqvdKdtBfHji8Be.png',
        ]);

        \App\Models\Productos::factory()->create([
            'nombre' => 'Smart TV LG 43UR81006LJ 43',
            'marca' => 'LG',
            'descripcion' => 'Buena Televisión',
            'categoria' => 'Televisions',
            'precio' => '580',
            'stock' => '10',
            'img' => 'EN4UxBbeFAHULiZE8YpI5GAalREV8AYlmgWTsxmF.png',
        ]);
    }
}