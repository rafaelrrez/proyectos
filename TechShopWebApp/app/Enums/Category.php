<?php

declare(strict_types=1);

namespace App\Enums;

enum Category: string {
    case GraphicCards = 'Graphic cards';
    case Televisions = 'Televisions';
    case Laptops = 'Laptops';
    case Headphones = 'Headphones';

    public static function getAllValues(): array
    {
        return static::getValues();
    }
}