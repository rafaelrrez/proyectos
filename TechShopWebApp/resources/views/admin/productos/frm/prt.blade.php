<div class="row">
    <div class="col-md-12">
        <section class="panel">
            <div class="panel-body">

                @if ( !empty ( $productos->id) )

                <div class="mb-3">
                    <label for="nombre" class="negrita">{{ __('name') }}:</label>
                    <div>
                        <input class="form-control" placeholder="Zapatos Marrones de Cuero" required="required"
                            name="nombre" type="text" id="nombre" value="{{ $productos->nombre }}">
                    </div>
                </div>

                <div class="mb-3">
                    <label for="marca" class="negrita">{{ __('branch_min') }}:</label>
                    <div>
                        <input class="form-control" placeholder="LG" required="required" name="marca" type="text"
                            id="marca" value="{{ $productos->marca }}">
                    </div>
                </div>

                <div class="mb-3">
                    <label for="descripcion" class="negrita">{{ __('description_min') }}:</label>
                    <div>
                        <input class="form-control" placeholder="Producto" required="required" name="descripcion"
                            type="text" id="descripcion" value="{{ $productos->descripcion }}">
                    </div>
                </div>

                <div class="mb-3">
                    <label for="categoria" class="negrita">{{ __('category_min') }}:</label>
                    <div>
                        <select class="form-select" name="categoria" id="categoria">
                            @foreach($categorias as $categoria)
                            <option value="{{ $categoria }}" @if(!empty($productos->categoria) && $productos->categoria
                                == $categoria) selected @endif>
                                {{ $categoria }}
                            </option>
                            @endforeach
                        </select>
                    </div>
                </div>

                <div class="mb-3">
                    <label for="precio" class="negrita">{{ __('price') }}:</label>
                    <div>
                        <input class="form-control" placeholder="4.50" required="required" name="precio" type="text"
                            id="precio" value="{{ $productos->precio }}">
                    </div>
                </div>

                <div class="mb-3">
                    <label for="stock" class="negrita">{{ __('stock') }}:</label>
                    <div>
                        <input class="form-control" placeholder="40" required="required" name="stock" type="text"
                            id="stock" value="{{ $productos->stock }}">
                    </div>
                </div>

                <div class="mb-3">
                    <label for="img" class="negrita">{{ __('select_image') }}:</label>
                    <div>
                        <input name="img" type="file" id="img">
                        <br>
                        <br>

                        @if ( !empty ( $productos->img) )

                        <span>{{ __('actual_image') }}: </span>
                        <br>
                        <img src="../../../uploads/{{ $productos->img }}" width="200" class="img-fluid">

                        @else

                        {{ __('no_image_for_product') }}

                        @endif

                    </div>

                </div>

                @else

                <div class="mb-3">
                    <label for="nombre" class="negrita">{{ __('name') }}:</label>
                    <div>
                        <input class="form-control" placeholder="Zapatos Marrones de Cuero" required="required"
                            name="nombre" type="text" id="nombre">
                    </div>
                </div>

                <div class="mb-3">
                    <label for="marca" class="negrita">{{ __('branch_min') }}:</label>
                    <div>
                        <input class="form-control" placeholder="LG" required="required" name="marca" type="text"
                            id="marca">
                    </div>
                </div>

                <div class="mb-3">
                    <label for="descripcion" class="negrita">{{ __('description_min') }}:</label>
                    <div>
                        <input class="form-control" placeholder="Producto" required="required" name="descripcion"
                            type="text" id="descripcion">
                    </div>
                </div>

                <div class="mb-3">
                    <label for="categoria" class="negrita">{{ __('category_min') }}:</label>
                    <div>
                        <select class="form-select" name="categoria" id="categoria">
                            @foreach($categorias as $categoria)
                            <option value="{{ $categoria }}" @if(!empty($productos->categoria) && $productos->categoria
                                == $categoria) selected @endif>
                                {{ $categoria }}
                            </option>
                            @endforeach
                        </select>
                    </div>
                </div>

                <div class="mb-3">
                    <label for="precio" class="negrita">{{ __('price') }}:</label>
                    <div>
                        <input class="form-control" placeholder="4.50" required="required" name="precio" type="text"
                            id="precio">
                    </div>
                </div>

                <div class="mb-3">
                    <label for="stock" class="negrita">{{ __('stock') }}:</label>
                    <div>
                        <input class="form-control" placeholder="40" required="required" name="stock" type="text"
                            id="stock">
                    </div>
                </div>

                <div class="mb-3">
                    <label for="img" class="negrita">{{ __('select_image') }}:</label>
                    <div>
                        <input name="img" type="file" id="img">
                    </div>
                </div>

                @endif

                <button type="submit" class="btn btn-info">{{ __('save') }}</button>
                <a href="{{ route('admin/productos') }}" class="btn btn-warning">{{ __('cancel') }}</a>

                <br>
                <br>

            </div>
        </section>
    </div>
</div>