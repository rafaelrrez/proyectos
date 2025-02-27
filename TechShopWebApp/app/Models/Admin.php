<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Foundation\Auth\User as Authenticatable;

class Admin extends Authenticatable
{
    use HasFactory; 
 
    // Instancio la tabla 'productos' 
    protected $table = 'admins';
    
    // Declaro los campos que usaré de la tabla 'productos' 
    protected $fillable = ['name', 'email', 'password', 'is_admin']; 
}
