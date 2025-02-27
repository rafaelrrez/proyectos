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

    <title>{{ __('home') }} | Tecno Market</title>

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
                                    <nav class="collapse navbar-collapse bs-navbar-collapse navbar-right" role="navigation">
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
                                        <a href="{{ route('admin/productos') }}">{{ __('products') }}</a>
                                    </li>
                                </ul>
                            </div>
                        </div>

                        <div class="col-md-10">

                            <nav aria-label="breadcrumb">
                                <ol class="breadcrumb">
                                    <li class="breadcrumb-item"><a href="{{ route('admin/productos') }}">{{ __('home') }}</a></li>
                                    <li class="breadcrumb-item active" aria-current="page">{{ __('products') }}</li>
                                </ol>
                            </nav>

                            <div class="row">

                                <div class="col-md-12">

                                    <div class="content-box-large">

                                        <div class="panel-heading">
                                            <div class="panel-title">
                                                <h2>{{ __('products') }}</h2>
                                            </div>

                                        </div>

                                        <div class="panel-body">

                                            @if(Session::has('message'))
                                            <div class="alert alert-primary" role="alert">
                                                {{ Session::get('message') }}
                                            </div>
                                            @endif


                                            <a href="{{ route('admin/productos/crear') }}" class="btn btn-success mt-4 ml-3"> {{ __('create') }}</a>

                                            <section class="example mt-4">

                                                <div class="table-responsive">

                                                    <table class="table table-striped table-bordered table-hover">
                                                        <thead>
                                                            <tr>
                                                                <th>{{ __('name') }}</th>
                                                                <th>{{ __('branch_min') }}</th>
                                                                <th>{{ __('description_min') }}</th>
                                                                <th>{{ __('category_min') }}</th>
                                                                <th>{{ __('price') }}</th>
                                                                <th>{{ __('stock') }}</th>
                                                                <th>{{ __('image') }}</th>
                                                                <th>{{ __('actions') }}</th>
                                                            </tr>
                                                        </thead>
                                                        <tbody>
                                                            @foreach($productos as $prod)
                                                            <tr>
                                                                <td class="v-align-middle">{{$prod->nombre}}</td>
                                                                <td class="v-align-middle">{{$prod->marca}}</td>
                                                                <td class="v-align-middle">{{$prod->descripcion}}</td>
                                                                <td class="v-align-middle">{{$prod->categoria}}</td>
                                                                <td class="v-align-middle">{{$prod->precio}}</td>
                                                                <td class="v-align-middle">{{$prod->stock}}</td>
                                                                <td class="v-align-middle">
                                                                    <img src="{!! asset("uploads/$prod->img") !!}" width="30" class="img-responsive">
                                                                </td>
                                                                <td class="v-align-middle">
                                                                    <form action="{{ route('admin/productos/eliminar',$prod->id) }}" method="POST" class="form-horizontal" role="form" onsubmit="return confirmarEliminar()">

                                                                        <input type="hidden" name="_method" value="PUT">
                                                                        <input type="hidden" name="_token" value="{{ csrf_token() }}">
                                                                        <a href="{{ route('admin/productos/detalles',$prod->id) }}" class="btn btn-dark">{{ __('details') }}</a>
                                                                        <a href="{{ route('admin/productos/actualizar',$prod->id) }}" class="btn btn-primary">{{ __('edit') }}</a>

                                                                        <button type="submit" class="btn btn-danger">{{ __('delete') }}</button>

                                                                    </form>

                                                                </td>
                                                            </tr>
                                                            @endforeach
                                                        </tbody>
                                                    </table>

                                                </div>
                                            </section>

                                        </div>

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