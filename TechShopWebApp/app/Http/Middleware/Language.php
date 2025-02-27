<?php

namespace App\Http\Middleware;

use Closure;
use App\Helpers\Helper;

final class Language
{
    public function handle($request, Closure $next)
    {
        setCurrentLanguage();
        return $next($request);
    }
}