@vite(['resources/js/app.js'])

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="Nube Colectiva">
    <link rel="shortcut icon" href="{{ asset('/img/favicon.png') }}" />
    <link rel="stylesheet" href="{{ asset('/css/admin/menu.css') }}" id="theme-stylesheet">

    <title>{{ __('detail') }} | Tecno Market</title>

</head>
<body>

    <header>
        <nav class="navbar navbar-expand-md navbar-dark bg-primary">
            <div class="container-fluid">
                <a class="navbar-brand d-md-none" href="#">
                    <img src="{{ asset('/img/logo.png') }}" alt="">
                </a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarNav">
                    <ul class="navbar-nav mx-auto">
                        <li class="nav-item">
                            <a class="nav-link active" aria-current="page" href="{{ route('admin/productos') }}">{{ __('home') }}</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link active" href="{{ route('admin/productos/crear') }}">{{ __('create') }}</a>
                        </li>
                        <li class="nav-item">
                            <a class="navbar-brand d-none d-md-block" href="#">
                                <img src="{{ asset('/img/logo_admin.png') }}" alt="">
                            </a>
                        </li>
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" href="#" id="languageDropdown" role="button" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                {{ __('language') }}
                            </a>
                            <div class="dropdown-menu" aria-labelledby="languageDropdown">
                                <a class="dropdown-item" href="{{ route('set_language', ['language' => 'en']) }}">
                                    <img src="{{ asset('img/icon_uk.png') }}" alt="Bandera de Inglesa" class="me-1 user-icon" style="max-width: 20px;"> {{ __('english') }}
                                </a>
                                <a class="dropdown-item" href="{{ route('set_language', ['language' => 'es']) }}">
                                    <img src="{{ asset('img/icon_espana.png') }}" alt="Bandera de España" class="me-1 user-icon" style="max-width: 20px;"> {{ __('spanish') }}
                                </a>
                            </div>
                        </li>
                        <li class="nav-item">
                            <form class="form-logout" method="post" action="{{ route('adminLogout') }}">
                                @csrf
                                <button type="submit" class="nav-link">{{ __('logout') }}</button>
                            </form>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>
    </header>

    <div class="container mt-5 mb-5">
        <div class="row">
            <div class="col-md-12">
                <div class="header">
                    <div class="container">
                        <div class="row">
                            <div class="col-md-5">
                                <!-- Logo -->
                                <div class="logo">
                                    <h1>{{ __('admin') }}</h1>
                                </div>
                            </div>
                            <div class="col-md-5">
                                <div class="row">
                                    <div class="col-lg-12">
                                        <div class="input-group form">
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-2">
                                <div class="navbar navbar-inverse" role="banner">
                                    <nav class="collapse navbar-collapse bs-navbar-collapse navbar-right"
                                        role="navigation">
                                        <ul class="nav navbar-nav">
                                            <li><a href="{{ route('admin/productos') }}">{{ __('admin') }}</a></li>
                                        </ul>
                                    </nav>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="page-content">
                    <div class="row">

                        <div class="col-md-2">
                            <div class="sidebar content-box" style="display: block;">

                                <ul class="list-group">
                                    <li class="list-group-item">
                                        <a href="{{ route('admin/productos') }}"> {{ __('products') }}</a>
                                    </li>
                                    <li class="list-group-item">
                                        {{ __('option1') }}
                                    </li>
                                    <li class="list-group-item">
                                    {{ __('option2') }}
                                    </li>
                                    <li class="list-group-item">
                                    {{ __('option3') }}
                                    </li>
                                    <li class="list-group-item">
                                    {{ __('option4') }}
                                    </li>
                                    <li class="list-group-item">
                                    {{ __('option5') }}
                                    </li>
                                    <li class="list-group-item">
                                    {{ __('option6') }}
                                    </li>
                                    <li class="list-group-item">
                                    {{ __('option7') }}
                                    </li>
                                    <li class="list-group-item">
                                    {{ __('option8') }}
                                    </li>
                                    <li class="list-group-item">
                                    {{ __('option9') }}
                                    </li>
                                    <li class="list-group-item">
                                    {{ __('option10') }}
                                    </li>
                                </ul>
                            </div>
                        </div>

                        <div class="col-md-10">

                            <nav aria-label="breadcrumb">
                                <ol class="breadcrumb">
                                    <li class="breadcrumb-item"><a href="{{ route('admin/productos') }}">{{ __('products') }}</a>
                                    </li>
                                    <li class="breadcrumb-item active" aria-current="page">{{ $productos->nombre }}</li>
                                </ol>
                            </nav>

                            <div class="row">

                                <div class="col-md-12">

                                    <div class="content-box-large">

                                        <div class="panel-body">

                                            @if(Session::has('message'))
                                            <div class="alert alert-primary" role="alert">
                                                {{ Session::get('message') }}
                                            </div>
                                            @endif

                                            <p class="h5">{{ __('name') }}:</p>
                                            <p class="h6 mb-3">{{ $productos->nombre }}</p>

                                            <p class="h5">{{ __('branch_min') }}:</p>
                                            <p class="h6 mb-3">{{ $productos->marca }}</p>

                                            <p class="h5">{{ __('description_min') }}:</p>
                                            <p class="h6 mb-3">{{ $productos->descripcion }}</p>

                                            <p class="h5">{{ __('category_min') }}:</p>
                                            <p class="h6 mb-3">{{ $productos->categoria }}</p>

                                            <p class="h5">{{ __('price') }}:</p>
                                            <p class="h6 mb-3">{{ $productos->precio }}</p>

                                            <p class="h5">{{ __('stock') }}:</p>
                                            <p class="h6 mb-3">{{ $productos->stock }}</p>

                                            <p class="h5">{{ __('image') }}:</p>

                                            <img src="{{ asset('uploads/' . $productos->img) }}" class="img-fluid" width="20%">

                                        </div>

                                        <a href="{{ route('admin/productos') }}" class="btn btn-warning mt-3">{{ __('back') }}</a>

                                    </div>

                                </div>

                            </div>

                        </div>

                    </div>

                </div>

            </div>

        </div>

    </div>

    <script type="text/javascript">
    function confirmarEliminar() {
        var x = confirm("Estas seguro de Eliminar?");
        if (x)
            return true;
        else
            return false;
    }
    </script>

</body>

</html>